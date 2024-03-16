package net.vellity.dynamic.servers.operations.watcher.rest

import net.vellity.dynamic.servers.common.http.server.utcNow
import net.vellity.dynamic.servers.operations.watcher.RegisteredWatcher
import net.vellity.dynamic.servers.operations.watcher.WatcherRegistry
import net.vellity.dynamic.servers.operations.watcher.rest.dto.WatcherRegistrationDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ServiceWatcherController(private val watcherRegistry: WatcherRegistry) : WatcherController {
  override fun registerWatcher(registration: WatcherRegistrationDto): ResponseEntity<Unit> {
    watcherRegistry.getWatcherOnExecutor(registration.executorId)?.let {
      watcherRegistry.unregister(it)
    }
    watcherRegistry.register(
      RegisteredWatcher(
        executor = registration.executorId,
        hostname = registration.hostname,
        port = registration.port,
        apiKey = registration.apiKey
      )
    )
    return ResponseEntity.ok().build()
  }

  override fun heartbeat(sourceName: String): ResponseEntity<Unit> {
    watcherRegistry.getWatcherOnExecutor(sourceName)?.let {
      it.lastHeartbeatAt = utcNow()
    }
    return ResponseEntity.ok().build()
  }
}