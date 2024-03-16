package net.vellity.dynamic.servers.startup.queue.http.client.dto

data class StartupQueueAddEntryDto(
  val templateId: String,
  val priority: Int,
  val environment: Map<String, String>
)