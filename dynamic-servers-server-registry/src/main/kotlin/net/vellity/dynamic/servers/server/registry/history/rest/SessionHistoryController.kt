package net.vellity.dynamic.servers.server.registry.history.rest

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@RequestMapping("/api/history")
interface SessionHistoryController {
  @GetMapping
  fun getHistory(@RequestParam serverId: String): ResponseEntity<ServerHistoryDto>

  @GetMapping("/find")
  fun findHistories(
    @RequestParam limit: Int,
    @RequestParam minDate: Long,
    @RequestParam maxDate: Long,
    @RequestParam(required = false, defaultValue = "") template: String,
    @RequestParam(required = false, defaultValue = "") executor: String
  ): ResponseEntity<List<ServerHistoryDto>>
}