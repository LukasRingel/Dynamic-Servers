package net.vellity.dynamic.servers.server.watcher.operations.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@RequestMapping("/api/operations")
interface OperationsController {
  @PostMapping("/restart")
  fun restartServer(@RequestParam("serverId") serverId: String): ResponseEntity<Unit>
}