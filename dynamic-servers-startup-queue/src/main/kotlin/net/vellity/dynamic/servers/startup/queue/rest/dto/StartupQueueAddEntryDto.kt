package net.vellity.dynamic.servers.startup.queue.rest.dto

data class StartupQueueAddEntryDto(
  val templateId: String,
  val priority: Int,
  val environment: Map<String, String>
)