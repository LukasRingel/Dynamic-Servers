package net.vellity.dynamic.servers.operations.operations.impl

import net.vellity.dynamic.servers.operations.operations.Operation
import net.vellity.dynamic.servers.operations.connection.registry.ServerRegistry
import net.vellity.dynamic.servers.operations.connection.startup.StartupQueue
import net.vellity.dynamic.servers.operations.connection.templates.TemplatesService
import net.vellity.dynamic.servers.operations.watcher.WatcherRegistry
import net.vellity.dynamic.servers.server.watcher.http.client.RetrofitWatcherClient
import net.vellity.dynamic.servers.templates.http.client.TemplatesClient
import org.slf4j.LoggerFactory

class RestartTemplateOperation(
  private val templateId: String,
  private val serviceRegistry: ServerRegistry,
  private val watcherRegistry: WatcherRegistry,
  private val templatesService: TemplatesService,
  private val startupQueue: StartupQueue
) : Operation {
  override fun execute() {
    logger.info("Restarting template $templateId")

    templatesService.updateTemplatesAndClearCache()
    logger.info("Updated templates to ensure we have the latest versions")

    val containerTemplate = templatesService.getContainerTemplate(templateId)
    if (containerTemplate == null) {
      logger.info("No template found for id $templateId")
      return
    }

    val allServersOfTemplate = serviceRegistry.allServersOfTemplate(templateId)
    for (registeredServerDto in allServersOfTemplate) {
      val watcherOnExecutor = watcherRegistry.getWatcherOnExecutor(registeredServerDto.executorId)

      if (watcherOnExecutor == null) {
        logger.info("No watcher found for executor ${registeredServerDto.executorId}")
        logger.info("Have to skip restart of server ${registeredServerDto.id}")
        continue
      }

      try {
        serviceRegistry.addTagsToServer(registeredServerDto.id.toString(), "Restarting")
        RetrofitWatcherClient(
          sourceName = "operations",
          apiKey = watcherOnExecutor.apiKey,
          baseUrl = watcherOnExecutor.hostname + ":" + watcherOnExecutor.port
        ).stopServer(registeredServerDto.id.toString())
        logger.info("Sent stop request for server ${registeredServerDto.id} to watcher on executor ${registeredServerDto.executorId}")

        startupQueue.insertTemplateToQueue(templateId, containerTemplate.startupPriority, emptyMap())
        logger.info("Queued $templateId server with priority ${containerTemplate.startupPriority}")
      } catch (e: Exception) {
        logger.error("Error while restarting server ${registeredServerDto.id}", e)
      }
    }

    logger.info("Finished operation: Restart of template $templateId")
  }

  companion object {
    private val logger = LoggerFactory.getLogger(RestartTemplateOperation::class.java)
  }
}