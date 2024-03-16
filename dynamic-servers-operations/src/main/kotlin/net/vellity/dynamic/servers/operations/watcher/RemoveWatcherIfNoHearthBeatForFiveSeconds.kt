package net.vellity.dynamic.servers.operations.watcher

import net.vellity.dynamic.servers.common.http.server.utcNow
import org.springframework.stereotype.Component
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@Component
class RemoveWatcherIfNoHearthBeatForFiveSeconds(private val watcherRegistry: WatcherRegistry) {
  init {
    Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
      this::tick,
      0,
      1,
      TimeUnit.SECONDS
    )
  }

  private fun tick() {
    val toRemove = mutableListOf<RegisteredWatcher>()
    for (registeredWatcher in watcherRegistry.allRegisteredWatchers()) {
      if (utcNow().minusSeconds(5) > registeredWatcher.lastHeartbeatAt) {
        toRemove.add(registeredWatcher)
      }
    }
    for (registeredWatcher in toRemove) {
      watcherRegistry.unregister(registeredWatcher)
    }
  }
}