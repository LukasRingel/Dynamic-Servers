package net.vellity.dynamic.servers.operations.operations.rest

import net.vellity.dynamic.servers.operations.operations.OperationsService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ServiceOperationsController(private val operationsService: OperationsService) : OperationsController {
  override fun restartTemplate(templateId: String, sourceName: String): ResponseEntity<Unit> {
    operationsService.restartTemplate(templateId)
    return ResponseEntity.ok().build()
  }
}