package net.vellity.dynamic.servers.startup.queue.rest.dto

import java.util.*

data class StartupQueueEntryDto(
  val id: UUID,
  val templateId: String,
  val environment: Map<String, String>
)