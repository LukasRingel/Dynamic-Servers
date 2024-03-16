package net.vellity.dynamic.servers.server.registry.registry

import net.vellity.dynamic.servers.common.http.server.utcNow
import java.time.Instant
import java.util.*

data class RegisteredServer(
  val id: UUID,
  val template: String,
  val executorId: String,
  val port: Int,
  val hostname: String,
  var status: ServerStatus = ServerStatus.STARTING,
  val createdAt: Instant = utcNow(),
  val tags: MutableList<String>,
  val additionalEnvironmentVars: Map<String, String>
)