package net.vellity.dynamic.servers.server.watcher.container.watcher

import net.vellity.dynamic.servers.server.registry.http.client.dto.ServerStatus
import net.vellity.dynamic.servers.server.watcher.container.ContainerService
import net.vellity.dynamic.servers.server.watcher.logs.collector.LogCollectorService
import net.vellity.dynamic.servers.server.watcher.logs.storage.LogStorageService
import net.vellity.dynamic.servers.server.watcher.serverregistry.ServerRegistry
import net.vellity.dynamic.servers.server.watcher.watchdog.WatchdogService
import net.vellity.dynamic.servers.server.watcher.watchdog.Watcher
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

class ContainerWatcher(
  private val containerId: String,
  private val containerService: ContainerService,
  private val serverRegistry: ServerRegistry,
  private val watchdogService: WatchdogService
) : Watcher {
  private val continueChecking = AtomicBoolean(true)

  override fun stop() {
    continueChecking.set(false)
  }

  override fun run() {
    while (continueChecking.get()) {
      if (containerService.isContainerRunning(containerId)) {
        Thread.sleep(ONE_SECOND)
        continue
      }
      val containerInfo = serverRegistry.getContainer(containerId)

      // server was removed from server registry
      if (containerInfo == null) {
        stopThread()
        continue
      }

      // server crashed white starting
      if (containerInfo.status == ServerStatus.STARTING && containerService.hasContainerStopped(containerId)) {
        stopThread()
        continue
      }

      // server is starting and there is no restart request
      if (containerInfo.status == ServerStatus.STARTING && !containerInfo.tags.contains("Restarting")) {
        Thread.sleep(ONE_SECOND)
        continue
      }

      stopThread()
    }
  }

  private fun stopThread() {
    serverRegistry.updateServerStatus(containerId, ServerStatus.STOPPED)
    watchdogService.stopWatchdog(containerId)
  }

  companion object {
    private const val ONE_SECOND = 1000L
  }
}