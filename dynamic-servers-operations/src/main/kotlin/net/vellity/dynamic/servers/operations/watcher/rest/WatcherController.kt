package net.vellity.dynamic.servers.operations.watcher.rest

import net.vellity.dynamic.servers.operations.watcher.rest.dto.WatcherRegistrationDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/api/watcher")
interface WatcherController {
  @PostMapping
  fun registerWatcher(@RequestBody registration: WatcherRegistrationDto): ResponseEntity<Unit>

  @PutMapping
  fun heartbeat(@RequestHeader("X-SOURCE-NAME") sourceName: String): ResponseEntity<Unit>
}