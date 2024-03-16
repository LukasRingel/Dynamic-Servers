package net.vellity.dynamic.servers.operations.watcher

interface WatcherRegistry {
  fun register(watcher: RegisteredWatcher)

  fun unregister(watcher: RegisteredWatcher)

  fun getWatcherOnExecutor(executor: String): RegisteredWatcher?

  fun allRegisteredWatchers(): List<RegisteredWatcher>
}