package net.vellity.dynamic.servers.operations.watcher

import net.vellity.dynamic.servers.common.http.server.utcNow
import java.time.Instant

data class RegisteredWatcher(
  val executor: String,
  val hostname: String,
  val port: Int,
  val apiKey: String,
  val registeredAt: Instant = utcNow(),
  var lastHeartbeatAt: Instant = utcNow()
)