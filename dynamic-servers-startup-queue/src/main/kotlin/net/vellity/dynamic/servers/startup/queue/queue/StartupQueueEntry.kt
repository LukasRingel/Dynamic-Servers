package net.vellity.dynamic.servers.startup.queue.queue

import net.vellity.dynamic.servers.common.http.server.utcNow
import java.time.Instant
import java.util.*

class StartupQueueEntry {
  var id: UUID = UUID.fromString("00000000-0000-0000-0000-000000000000")

  var template: String = ""

  var priority: Int = 0

  var addedAt: Instant = utcNow()

  var environment: Map<String, String> = emptyMap()
}