package net.vellity.dynamic.servers.server.starter.startup

import java.util.*

data class StartupTask(
  val id: UUID,
  val templateId: String,
  val environment: Map<String, String>
)