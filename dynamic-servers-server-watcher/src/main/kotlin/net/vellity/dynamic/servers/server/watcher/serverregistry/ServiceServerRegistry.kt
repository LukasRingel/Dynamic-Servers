package net.vellity.dynamic.servers.server.watcher.serverregistry

import net.vellity.dynamic.servers.common.http.server.environmentOrDefault
import net.vellity.dynamic.servers.server.registry.http.client.ServerRegistryClient
import net.vellity.dynamic.servers.server.registry.http.client.V1ServerRegistryClient
import net.vellity.dynamic.servers.server.registry.http.client.dto.RegisteredServerDto
import net.vellity.dynamic.servers.server.registry.http.client.dto.ServerStatus
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@Service
class ServiceServerRegistry(private val eventPublisher: ApplicationEventPublisher) : ServerRegistry {
  private val serverRegistryClient: ServerRegistryClient = V1ServerRegistryClient(
    hostname = environmentOrDefault("SERVER_REGISTRY_HOSTNAME", "http://localhost:8083"),
    apiKey = environmentOrDefault("SERVER_REGISTRY_API_KEY", "default-api-key"),
    sourceName = environmentOrDefault("SERVER_STARTER_NAME", "local")
  )

  private var currentDataFetch: List<RegisteredServerDto> = emptyList()

  init {
    Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
      this::update,
      0,
      1,
      TimeUnit.SECONDS
    )
  }

  private fun update() {
    val before = currentDataFetch
    currentDataFetch = serverRegistryClient.allServersOnExecutor(executorName)
    checkForStatusUpdatesAndCallEvent(before)
  }

  override fun getContainer(containerId: String): RegisteredServerDto? =
    currentDataFetch.find { it.id.toString() == containerId }

  override fun getServersOnExecutor(): List<RegisteredServerDto> =
    currentDataFetch

  override fun updateServerStatus(serverId: String, status: ServerStatus) {
    serverRegistryClient.updateServerStatus(serverId, status)
    currentDataFetch = currentDataFetch.map {
      if (it.id.toString() == serverId) {
        it.copy(status = status)
      } else {
        it
      }
    }
    eventPublisher.publishEvent(
      ContainerStatusUpdateEvent(
        currentDataFetch.first { dto -> dto.id.toString() == serverId },
        status
      )
    )
  }

  override fun removeServer(serverId: String, reason: String) {
    serverRegistryClient.deleteServer(serverId, reason)
  }

  // we only respect the status change if the server is still present in the registry
  private fun checkForStatusUpdatesAndCallEvent(previous: List<RegisteredServerDto>) {
    for (previousServer in previous) {
      val currentServer = currentDataFetch.find { it.id == previousServer.id }
      if (currentServer != null && currentServer.status != previousServer.status) {
        eventPublisher.publishEvent(ContainerStatusUpdateEvent(currentServer, currentServer.status))
      }
    }
  }

  companion object {
    private val executorName = environmentOrDefault("EXECUTOR_NAME", "local")
  }
}