package net.vellity.dynamic.servers.server.watcher.watchdog

import net.vellity.dynamic.servers.server.watcher.container.watcher.ContainerWatcher
import net.vellity.dynamic.servers.server.watcher.container.watcher.ContainerWatcherService
import net.vellity.dynamic.servers.server.watcher.logs.watcher.LogStreamWatcher
import net.vellity.dynamic.servers.server.watcher.logs.watcher.LogWatcherService
import net.vellity.dynamic.servers.server.watcher.serverregistry.ServerRegistry
import org.springframework.stereotype.Service

@Service
class AllWatchersWatchdogService(
  private val logWatcherService: LogWatcherService,
  private val containerWatcherService: ContainerWatcherService,
  private val serverRegistry: ServerRegistry
) : WatchdogService {
  private val watchdogs: MutableMap<String, Watchdog> = mutableMapOf()

  override fun startWatchdog(containerId: String) {
    val server = serverRegistry.getContainer(containerId) ?: return

    val logWatcher: LogStreamWatcher = logWatcherService.createLogWatcher(containerId, server.tags)
    val containerWatcher: ContainerWatcher = containerWatcherService.createContainerWatcher(containerId, this)

    watchdogs[containerId] = Watchdog(
      containerId = containerId,
      logStreamWatcher = logWatcher,
      containerWatcher = containerWatcher
    )
    watchdogs[containerId]!!.start()
  }

  override fun isWatchdogRunning(containerId: String): Boolean =
    watchdogs.containsKey(containerId) && watchdogs[containerId]!!.isRunning()

  override fun stopWatchdog(containerId: String) {
    watchdogs[containerId]?.stop()
    watchdogs.remove(containerId)
  }
}