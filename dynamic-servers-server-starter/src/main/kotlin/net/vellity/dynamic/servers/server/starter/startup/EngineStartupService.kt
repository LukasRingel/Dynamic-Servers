package net.vellity.dynamic.servers.server.starter.startup

import net.vellity.dynamic.servers.common.http.server.environmentOrDefault
import net.vellity.dynamic.servers.server.starter.connection.registry.ServerRegistry
import net.vellity.dynamic.servers.server.starter.connection.templates.TemplatesProvider
import net.vellity.dynamic.servers.server.starter.engine.ContainerEngine
import net.vellity.dynamic.servers.server.starter.startup.port.PortResolver
import net.vellity.dynamic.servers.templates.http.client.ContainerTemplateInformation
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.concurrent.Executors

@Component
class EngineStartupService(
  private val portResolver: PortResolver,
  private val containerEngine: ContainerEngine,
  private val templatesProvider: TemplatesProvider,
  private val serverRegistry: ServerRegistry,
) : StartupTaskService {
  override fun start(task: StartupTask) {
    executors.submit {
      try {
        val containerTemplateInformation = templatesProvider.getContainerTemplateInformation(task.templateId)
          ?: return@submit
        logger.info("Found template information for ${task.id} of type ${task.templateId}")

        val port = portResolver.findFreePort()
        logger.info("Starting container for ${task.id} of type ${task.templateId} on port $port")

        val containerId = createContainer(task, containerTemplateInformation, port)
        registerServer(task, port, containerTemplateInformation.internalTags, task.environment)
        containerEngine.startContainer(containerId)
      } catch (e: Exception) {
        logger.error("Failed to start container for ${task.id} of type ${task.templateId}", e)
      }
    }
  }

  private fun createContainer(
    task: StartupTask,
    containerTemplateInformation: ContainerTemplateInformation,
    port: Int
  ): String = containerEngine.createContainer(
    containerName = task.id.toString(),
    containerImage = containerTemplateInformation.image,
    environment = appendInternalEnvironmentVariables(
      containerTemplateInformation.environment.plus(task.environment),
      port,
      task
    ),
    port = port,
    templateId = task.templateId,
    tags = containerTemplateInformation.internalTags,
    maximumMemoryInMB = containerTemplateInformation.maximumMemoryInMB
  )

  private fun registerServer(task: StartupTask, port: Int, tags: List<String>, additionalEnvironmentVars: Map<String, String>) {
    serverRegistry.registerServer(
      serverId = task.id.toString(),
      template = task.templateId,
      port = port,
      hostname = serverStarterHostname,
      tags = tags,
      additionalEnvironmentVars = additionalEnvironmentVars
    )
  }

  private fun appendInternalEnvironmentVariables(
    vars: Map<String, String>,
    port: Int,
    task: StartupTask
  ): Map<String, String> {
    return vars + mapOf(
      "DYNAMIC_SERVERS_CONTAINER_HOSTNAME" to serverStarterHostname,
      "DYNAMIC_SERVERS_CONTAINER_PORT" to port.toString(),
      "DYNAMIC_SERVERS_CONTAINER_ID" to task.id.toString(),
      "DYNAMIC_SERVERS_CONTAINER_TEMPLATE" to task.templateId,
      "DYNAMIC_SERVERS_SERVER_STARTER_NAME" to serverStarterName,
      "DYNAMIC_SERVERS_SERVER_REGISTRY_HOSTNAME" to serverRegistryUrl(),
      "DYNAMIC_SERVERS_SERVER_REGISTRY_API_KEY" to serverRegistry.apiKey()
    )
  }

  private fun serverRegistryUrl(): String {
    val url = serverRegistry.hostname()
    return if (url.contains("localhost")) {
      url.replace("localhost", "host.docker.internal")
    } else {
      url
    }
  }

  companion object {
    private val executors = Executors.newCachedThreadPool()
    private val logger = LoggerFactory.getLogger(StartupTaskService::class.java)
    private val serverStarterHostname = environmentOrDefault("SERVER_STARTER_HOSTNAME", "localhost")
    private val serverStarterName = environmentOrDefault("SERVER_STARTER_NAME", "local")
  }
}