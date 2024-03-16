package net.vellity.dynamic.servers.server.watcher.container

import net.vellity.dynamic.servers.server.registry.http.client.dto.ServerStatus
import net.vellity.dynamic.servers.server.watcher.serverregistry.ContainerStatusUpdateEvent
import net.vellity.dynamic.servers.server.watcher.serverregistry.ServiceServerRegistry
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import java.util.concurrent.Executors

@Component
class DeleteContainerOnReadyToDelete(
  private val containerService: ContainerService,
  private val serverRegistry: ServiceServerRegistry
) : ApplicationListener<ContainerStatusUpdateEvent> {
  override fun onApplicationEvent(event: ContainerStatusUpdateEvent) {
    if (event.newStatus != ServerStatus.READY_TO_DELETE) {
      return
    }

    val serverId = event.server.id.toString()

    executor.submit {
      serverRegistry.updateServerStatus(serverId, ServerStatus.DELETED)
      containerService.deleteContainer(serverId)
      serverRegistry.removeServer(serverId, CONTAINER_STOPPED_REASON)
    }
  }

  companion object {
    private val executor = Executors.newCachedThreadPool()
    private const val CONTAINER_STOPPED_REASON = "Container stopped"
  }
}