package net.vellity.dynamic.servers.server.watcher.watchdog

import net.vellity.dynamic.servers.server.registry.http.client.dto.RegisteredServerDto
import net.vellity.dynamic.servers.server.registry.http.client.dto.ServerStatus
import net.vellity.dynamic.servers.server.watcher.monitoring.Incident
import net.vellity.dynamic.servers.server.watcher.monitoring.MonitoringService
import net.vellity.dynamic.servers.server.watcher.serverregistry.ServerRegistry
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.net.ConnectException
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@Component
class StartRequiredWatchdogsEverySecond(
  private val serverRegistry: ServerRegistry,
  private val watchdogService: WatchdogService,
  private val monitoringService: MonitoringService
) {
  init {
    Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
      this::tick,
      500,
      500,
      TimeUnit.MILLISECONDS
    )
  }

  private fun tick() {
    try {
      for (server in serverRegistry.getServersOnExecutor()) {
        if (watchdogService.isWatchdogRunning(server.id.toString())) {
          continue
        }
        if (shouldNotStartWatchdog(server)) {
          continue
        }
        watchdogService.startWatchdog(server.id.toString())
      }
    } catch (e: ConnectException) {
      logger.warn("Could not connect to server registry, skipping watchdog start tick")
      monitoringService.announceIncident(Incident.SERVER_REGISTRY_UNREACHABLE)
    }
  }

  private fun shouldNotStartWatchdog(server: RegisteredServerDto): Boolean =
    server.status != ServerStatus.STARTING &&
      server.status != ServerStatus.RUNNING &&
      server.status != ServerStatus.STOPPING

  companion object {
    private val logger = LoggerFactory.getLogger(StartRequiredWatchdogsEverySecond::class.java)
  }
}