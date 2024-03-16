package net.vellity.dynamic.servers.startup.queue.rest

import net.vellity.dynamic.servers.common.http.server.filter.RequestSourceNameFilter
import net.vellity.dynamic.servers.startup.queue.rest.dto.StartupQueueAddEntryDto
import net.vellity.dynamic.servers.startup.queue.rest.dto.StartupQueueEntryDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RequestMapping("/api/queue")
interface StartupQueueController {
  @GetMapping("/next")
  fun pollNextServerToStart(@RequestHeader(RequestSourceNameFilter.SOURCE_NAME_HEADER) sourceName: String): ResponseEntity<StartupQueueEntryDto?>

  @GetMapping
  fun getAllServersToStart(): ResponseEntity<List<StartupQueueEntryDto>>

  @PostMapping
  fun addServerToStart(
    @RequestBody startupQueueAddEntryDto: StartupQueueAddEntryDto,
    @RequestHeader(RequestSourceNameFilter.SOURCE_NAME_HEADER) sourceName: String
  ): ResponseEntity<UUID>

  @DeleteMapping("/all")
  fun deleteAllEntries(@RequestHeader(RequestSourceNameFilter.SOURCE_NAME_HEADER) sourceName: String): ResponseEntity<Unit>
}