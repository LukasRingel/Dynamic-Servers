package net.vellity.dynamic.servers.server.starter.engine

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.api.command.CreateContainerResponse
import com.github.dockerjava.api.command.PullImageResultCallback
import com.github.dockerjava.api.model.ExposedPort
import com.github.dockerjava.api.model.HostConfig
import com.github.dockerjava.api.model.PortBinding
import com.github.dockerjava.core.DefaultDockerClientConfig
import com.github.dockerjava.core.DockerClientImpl
import com.github.dockerjava.zerodep.ZerodepDockerHttpClient
import net.vellity.dynamic.servers.server.starter.connection.templates.TemplatesProvider
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.io.InputStream
import java.net.URI
import java.time.Duration
import java.util.concurrent.Executors
import kotlin.math.max

@Component
class DockerContainerEngine(
  private val dockerEngineConfiguration: DockerEngineConfiguration,
  private val templatesProvider: TemplatesProvider
) : ContainerEngine {
  private var dockerHttpClient: DockerClient? = DockerClientImpl.getInstance(
    DefaultDockerClientConfig.createDefaultConfigBuilder()
      .withDockerHost(dockerEngineConfiguration.hostname())
      .withDockerTlsVerify(dockerEngineConfiguration.verifyTsl())
      .withRegistryUrl(dockerEngineConfiguration.registryUrl())
      .withRegistryUsername(dockerEngineConfiguration.registryUsername())
      .withRegistryPassword(dockerEngineConfiguration.registryPassword())
      .build(),
    ZerodepDockerHttpClient.Builder()
      .dockerHost(URI.create(dockerEngineConfiguration.hostname()))
      .connectionTimeout(Duration.ofSeconds(1))
      .responseTimeout(Duration.ofSeconds(1))
      .build()
  )

  override fun createContainer(
    containerName: String,
    containerImage: String,
    environment: Map<String, String>,
    port: Int,
    templateId: String,
    tags: List<String>,
    maximumMemoryInMB: Int
  ): String {
    try {
      pullContainerImageIfRequired(containerImage)
      logger.info("Pulled image $containerImage for $containerName")

      val containerResponse = executeCreateContainerCommand(containerImage, containerName, environment, port, tags, maximumMemoryInMB)
      logger.info("Created container for $containerName")

      if (containerResponse == null) {
        logger.error("Failed to create container for $containerName")
        return ""
      }

      copyTemplatesToContainer(containerResponse, templatesProvider.downloadFilesOfTemplate(templateId))
      logger.info("Template downloaded and unarchived for $containerName")
      logger.info("Finished setup of container for $containerName")
      return containerResponse.id
    } catch (e: Exception) {
      logger.error("Failed to create container for $containerName", e)
      return ""
    }
  }

  override fun startContainer(container: String) {
    dockerHttpClient?.startContainerCmd(container)?.exec()
    logger.info("Started container $container")
  }

  private fun pullContainerImageIfRequired(containerImage: String) {
    dockerHttpClient?.pullImageCmd(replacePrivateRegistry(containerImage))
      ?.exec(PullImageResultCallback())
      ?.awaitCompletion()
  }

  private fun copyTemplatesToContainer(
    containerResponse: CreateContainerResponse,
    it: InputStream
  ) = dockerHttpClient?.copyArchiveToContainerCmd(containerResponse.id)
    ?.withTarInputStream(it)
    ?.withRemotePath(CONTAINER_WORKING_DIR)
    ?.withCopyUIDGID(false)
    ?.exec()

  private fun executeCreateContainerCommand(
    containerImage: String,
    containerName: String,
    environment: Map<String, String>,
    port: Int,
    tags: List<String>,
    maximumMemoryInMB: Int
  ): CreateContainerResponse? =
    dockerHttpClient!!.createContainerCmd(replacePrivateRegistry(containerImage))
      .withName(containerName)
      .withEnv(environment.map { "${it.key}=${it.value}" })
      .withIpv4Address(EXPOSED_HOST)
      .withHostName(EXPOSED_HOST)
      .withExposedPorts(ExposedPort.tcp(MINECRAFT_PORT))
      .withWorkingDir(CONTAINER_WORKING_DIR)
      .withAttachStderr(true)
      .withLabels(getLabelsForContainer(tags))
      .withHostConfig(
        HostConfig.newHostConfig()
          .withPortBindings(PortBinding.parse("$port:$MINECRAFT_PORT"))
          .withMemory(maximumMemoryInMB.toLong() * 1024 * 1024)
      )
      .exec()

  override fun stopContainer(containerName: String) {
    dockerHttpClient?.stopContainerCmd(containerName)?.exec()
  }

  override fun runningContainersCount(): Int {
    return dockerHttpClient?.listContainersCmd()
      ?.withLabelFilter(listOf(CONTAINER_LABEL))
      ?.exec()
      ?.size
      ?: 0
  }

  private fun replacePrivateRegistry(image: String): String {
    return image.replace(
      "private/", dockerEngineConfiguration.registryUrl()
        .replace("http://", "")
        .replace("https://", "") +
        "/"
    )
  }

  private fun getLabelsForContainer(tags: List<String>): Map<String, String> {
    return mapOf(
      CONTAINER_LABEL to "true",
      "net.vellity.dynamic.servers.tags" to tags.joinToString(","),
    )
  }

  companion object {
    private val logger = LoggerFactory.getLogger(ContainerEngine::class.java)
    private const val MINECRAFT_PORT = 25565
    private const val EXPOSED_HOST = "0.0.0.0"
    private const val CONTAINER_WORKING_DIR = "/data/minecraft"
    private const val CONTAINER_LABEL = "net.vellity.dynamic.servers"
  }
}