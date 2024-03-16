package net.vellity.dynamic.servers.server.watcher.container.watcher

import net.vellity.dynamic.servers.server.watcher.container.ContainerService
import net.vellity.dynamic.servers.server.watcher.serverregistry.ServerRegistry
import net.vellity.dynamic.servers.server.watcher.watchdog.WatchdogService
import org.springframework.stereotype.Component

@Component
class DefaultContainerWatcherService(
  private val containerService: ContainerService,
  private val serverRegistry: ServerRegistry
) : ContainerWatcherService {
  override fun createContainerWatcher(containerId: String, watchdogService: WatchdogService): ContainerWatcher =
    ContainerWatcher(
      containerId,
      containerService,
      serverRegistry,
      watchdogService
    )
}