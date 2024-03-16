package net.vellity.dynamic.servers.startup.queue.rest

import net.vellity.dynamic.servers.common.http.server.utcNow
import net.vellity.dynamic.servers.startup.queue.queue.StartupQueue
import net.vellity.dynamic.servers.startup.queue.queue.StartupQueueEntry
import net.vellity.dynamic.servers.startup.queue.rest.dto.StartupQueueAddEntryDto
import net.vellity.dynamic.servers.startup.queue.rest.dto.StartupQueueEntryDto
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class DirectStartupQueueController(private val startupQueue: StartupQueue) : StartupQueueController {
  override fun pollNextServerToStart(sourceName: String): ResponseEntity<StartupQueueEntryDto?> {
    val entry = startupQueue.poll() ?: return ResponseEntity.ok(null)
    logger.info(sourceName + " polled next server to start: " + entry.template)
    return ResponseEntity.ok(mapEntryToDto(entry))
  }

  override fun getAllServersToStart(): ResponseEntity<List<StartupQueueEntryDto>> {
    return ResponseEntity.ok(startupQueue.all().map { mapEntryToDto(it) })
  }

  override fun addServerToStart(
    startupQueueAddEntryDto: StartupQueueAddEntryDto,
    sourceName: String
  ): ResponseEntity<UUID> {
    val queueEntry = mapAddRequestToEntry(startupQueueAddEntryDto)
    startupQueue.add(queueEntry)
    logger.info(sourceName + " added server to start: " + queueEntry.template + " with priority " + queueEntry.priority)
    return ResponseEntity.ok(queueEntry.id)
  }

  override fun deleteAllEntries(sourceName: String): ResponseEntity<Unit> {
    startupQueue.clear()
    logger.info("$sourceName cleared all entries")
    return ResponseEntity.ok(Unit)
  }

  private fun mapEntryToDto(entry: StartupQueueEntry): StartupQueueEntryDto {
    return StartupQueueEntryDto(entry.id, entry.template, entry.environment)
  }

  private fun mapAddRequestToEntry(startupQueueEntryDto: StartupQueueAddEntryDto): StartupQueueEntry {
    return StartupQueueEntry().apply {
      id = UUID.randomUUID()
      template = startupQueueEntryDto.templateId
      priority = startupQueueEntryDto.priority
      addedAt = utcNow()
      environment = startupQueueEntryDto.environment
    }
  }

  companion object {
    private val logger = LoggerFactory.getLogger(StartupQueueController::class.java)
  }
}