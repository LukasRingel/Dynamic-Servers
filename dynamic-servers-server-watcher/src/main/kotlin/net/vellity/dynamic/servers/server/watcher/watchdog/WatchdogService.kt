package net.vellity.dynamic.servers.server.watcher.watchdog

interface WatchdogService {
  fun startWatchdog(containerId: String)

  fun isWatchdogRunning(containerId: String): Boolean

  fun stopWatchdog(containerId: String)
}