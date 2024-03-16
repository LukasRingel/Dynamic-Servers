package net.vellity.dynamic.servers.server.watcher.container.docker

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.api.model.Container
import com.github.dockerjava.core.DefaultDockerClientConfig
import com.github.dockerjava.core.DockerClientImpl
import com.github.dockerjava.zerodep.ZerodepDockerHttpClient
import net.vellity.dynamic.servers.server.watcher.container.ContainerEngine
import org.springframework.stereotype.Component
import java.net.URI
import java.time.Duration
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@Component
class DockerContainerEngine(dockerEngineConfiguration: DockerEngineConfiguration) : ContainerEngine {
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
      .connectionTimeout(Duration.ofDays(3))
      .responseTimeout(Duration.ofDays(3))
      .build()
  )

  private var lastDataFetch: List<Container> = listOf()

  init {
    Executors.newSingleThreadScheduledExecutor()
      .scheduleAtFixedRate(
        this::fetchData,
        0,
        1, TimeUnit.SECONDS
      )
  }

  override fun isContainerRunning(containerName: String): Boolean {
    return lastDataFetch.any { container ->
      container.names.contains("/$containerName") &&
        container.state == "running"
    }
  }

  override fun hasContainerStopped(containerName: String): Boolean {
    return lastDataFetch.any { container ->
      container.names.contains("/$containerName") &&
        container.state == "exited"
    }
  }

  override fun anyContainerWithName(containerName: String): Boolean {
    return lastDataFetch.any { container -> container.names.contains("/$containerName") }
  }

  override fun stopContainer(containerName: String) {
    lastDataFetch.filter { container -> container.names.contains("/$containerName") }
      .forEach { container ->
        run {
          try {
            dockerHttpClient?.stopContainerCmd(container.id)?.exec()
          } catch (ignored: Exception) {
            // ignored because the container might already be stopped
            // and docker throws an exception in every case
          }
        }
      }
  }

  override fun deleteContainer(containerName: String) {
    lastDataFetch.filter { container -> container.names.contains("/$containerName") }
      .forEach { container -> dockerHttpClient?.removeContainerCmd(container.id)?.exec() }
  }

  override fun logsForContainer(containerName: String): ByteArray {
    val resultToByteArray = DockerLogResultToByteArray()
    dockerHttpClient?.logContainerCmd(containerName)
      ?.withStdOut(true)
      ?.withStdErr(true)
      ?.exec(resultToByteArray)
      ?.awaitCompletion()
    return resultToByteArray.byteArray
  }

  override fun streamLogsForContainer(containerName: String, consumer: (ByteArray) -> Unit) {
    val resultToByteArray = DockerLogResultToConsumer(consumer)
    dockerHttpClient?.attachContainerCmd(containerName)
      ?.withStdOut(true)
      ?.withStdErr(true)
      ?.withFollowStream(true)
      ?.withTimestamps(false)
      ?.exec(resultToByteArray)
      ?.awaitCompletion()
  }

  private fun allContainers(): List<Container> {
    return dockerHttpClient?.listContainersCmd()
      ?.withLabelFilter(listOf(CONTAINER_LABEL))
      ?.withShowAll(true)
      ?.withLimit(100)
      ?.exec()
      ?: emptyList()
  }

  private fun fetchData() {
    lastDataFetch = allContainers()
  }

  companion object {
    private const val CONTAINER_LABEL = "net.vellity.dynamic.servers"
  }
}