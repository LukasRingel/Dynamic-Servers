package net.vellity.dynamic.servers.server.watcher.logs.watcher

interface LogWatcherService {
  fun createLogWatcher(containerId: String, tags: List<String>): LogStreamWatcher
}