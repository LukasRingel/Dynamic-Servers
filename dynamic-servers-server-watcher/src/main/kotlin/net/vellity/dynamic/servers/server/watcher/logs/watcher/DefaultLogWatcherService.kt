package net.vellity.dynamic.servers.server.watcher.logs.watcher

import net.vellity.dynamic.servers.server.registry.http.client.dto.ServerStatus
import net.vellity.dynamic.servers.server.watcher.container.ContainerService
import net.vellity.dynamic.servers.server.watcher.logs.collector.LogCollectorService
import net.vellity.dynamic.servers.server.watcher.monitoring.Incident
import net.vellity.dynamic.servers.server.watcher.monitoring.MonitoringService
import net.vellity.dynamic.servers.server.watcher.serverregistry.ServerRegistry
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class DefaultLogWatcherService(
  private val logCollectorService: LogCollectorService,
  private val serverRegistry: ServerRegistry,
  private val containerService: ContainerService,
  private val monitoringService: MonitoringService
) : LogWatcherService {
  override fun createLogWatcher(containerId: String, tags: List<String>) =
    LogStreamWatcherFactory.findForTags(
      tags = tags,
      containerId = containerId,
      logCollectorService = logCollectorService,
      onContainerReady = { containerReady(containerId, it) },
      onContainerStartToStop = { containerStopping(containerId) }
    )

  private fun containerReady(containerId: String, instance: LogStreamWatcher) {
    serverRegistry.updateServerStatus(containerId, ServerStatus.RUNNING)
    if (instance.detectedAnyExceptions()) {
      logger.info("Detected exceptions in container $containerId while starting. Stopping...")
      monitoringService.announceIncident(Incident.CONTAINER_STARTED_WITH_EXCEPTIONS, containerId)
      containerService.stopContainer(containerId)
    } else {
      logger.info("Container $containerId is ready")
    }
  }

  private fun containerStopping(containerId: String) {
    logger.info("Container $containerId is starting to stop...")
    serverRegistry.updateServerStatus(containerId, ServerStatus.STOPPING)
  }

  companion object {
    private val logger = LoggerFactory.getLogger(DefaultLogWatcherService::class.java)
  }
}