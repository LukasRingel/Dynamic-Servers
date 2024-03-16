package net.vellity.dynamic.servers.templates.updates.rest

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/api/updates")
interface UpdateController {
  @PostMapping
  fun update(): ResponseEntity<Unit>
}