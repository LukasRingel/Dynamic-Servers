package net.vellity.dynamic.servers.startup.queue.http.client

import net.vellity.dynamic.servers.startup.queue.http.client.dto.StartupQueueEntryDto
import java.util.*

interface StartupQueueClient {
  fun pollNextServerToStart(): StartupQueueEntryDto?

  fun getAllServersToStart(): List<StartupQueueEntryDto>

  fun addServerToStart(templateId: String, priority: Int, environment: Map<String, String>): UUID?

  fun deleteAllEntries()
}