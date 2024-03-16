package net.vellity.dynamic.servers.server.watcher.logs

import net.vellity.dynamic.servers.server.registry.http.client.dto.ServerStatus
import net.vellity.dynamic.servers.server.watcher.logs.collector.LogCollectorService
import net.vellity.dynamic.servers.server.watcher.logs.storage.LogStorageService
import net.vellity.dynamic.servers.server.watcher.monitoring.Incident
import net.vellity.dynamic.servers.server.watcher.monitoring.MonitoringService
import net.vellity.dynamic.servers.server.watcher.serverregistry.ContainerStatusUpdateEvent
import net.vellity.dynamic.servers.server.watcher.serverregistry.ServiceServerRegistry
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import java.util.concurrent.Executors

@Component
class UploadLogOnContainerStop(
  private val logCollectorService: LogCollectorService,
  private val logStorageService: LogStorageService,
  private val serverRegistry: ServiceServerRegistry,
  private val monitoringService: MonitoringService
) : ApplicationListener<ContainerStatusUpdateEvent> {
  override fun onApplicationEvent(event: ContainerStatusUpdateEvent) {
    if (event.newStatus != ServerStatus.STOPPED) {
      return
    }

    val serverId = event.server.id.toString()

    executor.submit {
      serverRegistry.updateServerStatus(serverId, ServerStatus.SAVING_LOG)
      try {
        val logForServer = logCollectorService.collectLogForServer(serverId)
        logStorageService.storeLog(serverId, logForServer)
        serverRegistry.updateServerStatus(serverId, ServerStatus.SAVING_LOG_SUCCESS)
      } catch (e: Exception) {
        serverRegistry.updateServerStatus(serverId, ServerStatus.SAVING_LOG_FAILED)
        monitoringService.announceIncident(Incident.CONTAINER_LOG_SAVE_FAILED, serverId)
        return@submit
      }
    }
  }

  companion object {
    private val executor = Executors.newCachedThreadPool()
  }
}