package net.vellity.dynamic.servers.server.watcher.container.watcher

import net.vellity.dynamic.servers.server.watcher.watchdog.WatchdogService

interface ContainerWatcherService {
  fun createContainerWatcher(containerId: String, watchdogService: WatchdogService): ContainerWatcher
}