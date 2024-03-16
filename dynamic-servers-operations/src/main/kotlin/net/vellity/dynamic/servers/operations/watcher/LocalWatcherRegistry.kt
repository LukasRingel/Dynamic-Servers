package net.vellity.dynamic.servers.operations.watcher

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class LocalWatcherRegistry : WatcherRegistry {
  private val watchers = mutableListOf<RegisteredWatcher>()
  override fun register(watcher: RegisteredWatcher) {
    watchers.add(watcher)
    logger.info("Registered watcher for ${watcher.executor} on socket ${watcher.hostname}:${watcher.port}")
  }

  override fun unregister(watcher: RegisteredWatcher) {
    watchers.remove(watcher)
    logger.info("Removed watcher for ${watcher.executor}")
  }

  override fun getWatcherOnExecutor(executor: String): RegisteredWatcher? {
    return watchers.firstOrNull { it.executor == executor }
  }

  override fun allRegisteredWatchers(): List<RegisteredWatcher> {
    return watchers.toList()
  }

  companion object {
    private val logger = LoggerFactory.getLogger(WatcherRegistry::class.java)
  }
}