package net.vellity.dynamic.servers.server.watcher.container.watcher

import net.vellity.dynamic.servers.common.http.server.utcNow
import net.vellity.dynamic.servers.server.watcher.container.ContainerService
import net.vellity.dynamic.servers.server.watcher.monitoring.Incident
import net.vellity.dynamic.servers.server.watcher.monitoring.MonitoringService
import net.vellity.dynamic.servers.server.watcher.serverregistry.ServerRegistry
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@Component
class ScanForNotExistingContainers(
  private val containerService: ContainerService,
  private val serverRegistry: ServerRegistry,
  private val monitoringService: MonitoringService
) {
  init {
    Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
      this::tick,
      0,
      1,
      TimeUnit.SECONDS
    )
  }

  private fun tick() {
    val lastDataFetch = serverRegistry.getServersOnExecutor()
    for (registeredServerDto in lastDataFetch) {

      if (containerService.anyContainerWithName(registeredServerDto.id.toString())) {
        continue
      }

      if (!hasBeenCreatedMoreThan10SecondsAgo(registeredServerDto.createdAt)) {
        continue
      }

      serverRegistry.removeServer(registeredServerDto.id.toString(), "Container never started")
      monitoringService.announceIncident(Incident.CONTAINER_NEVER_STARTED, registeredServerDto.id.toString())
    }
  }

  private fun hasBeenCreatedMoreThan10SecondsAgo(createdAt: Long): Boolean =
    Instant.ofEpochMilli(createdAt).isBefore(utcNow().minusSeconds(10))
}