package net.vellity.dynamic.servers.templates.updates.rest

import net.vellity.dynamic.servers.templates.updates.UpdateService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ServiceUpdateController(private val updateService: UpdateService) : UpdateController {
  override fun update(): ResponseEntity<Unit> {
    updateService.update()
    return ResponseEntity.ok().build()
  }
}