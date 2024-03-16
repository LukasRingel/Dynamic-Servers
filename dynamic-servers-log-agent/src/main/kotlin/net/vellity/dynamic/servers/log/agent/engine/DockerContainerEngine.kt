package net.vellity.dynamic.servers.log.agent.engine

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.api.command.AttachContainerCmd
import com.github.dockerjava.api.model.Frame
import com.github.dockerjava.core.DefaultDockerClientConfig
import com.github.dockerjava.core.DockerClientImpl
import com.github.dockerjava.core.InvocationBuilder
import com.github.dockerjava.zerodep.ZerodepDockerHttpClient
import org.springframework.stereotype.Service
import java.net.URI
import java.time.Duration

@Service
class DockerContainerEngine(dockerEngineConfiguration: DockerEngineConfiguration): EngineLogCollectorService {
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

  private val sessions: MutableMap<String, InvocationBuilder.AsyncResultCallback<Frame>?> = mutableMapOf()

  override fun streamLogsForServer(serverId: String, consumer: (String) -> Unit) {
    Thread {
      val resultToByteArray = DockerLogResultToConsumer(consumer)
      val resultCallback = dockerHttpClient?.attachContainerCmd(serverId)
        ?.withStdOut(true)
        ?.withStdErr(true)
        ?.withFollowStream(true)
        ?.withTimestamps(false)
        ?.withLogs(true)
        ?.exec(resultToByteArray)
        ?.awaitCompletion()
      sessions[serverId] = resultCallback
    }.start()
  }

  override fun stopStreamingLogsForServer(serverId: String) {
    sessions[serverId]?.close()
    sessions.remove(serverId)
  }
}