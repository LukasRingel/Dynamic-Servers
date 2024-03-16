package net.vellity.dynamic.servers.server.watcher.operations.controller

import net.vellity.dynamic.servers.server.watcher.container.ContainerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class DirectOperationsController(private val containerService: ContainerService) : OperationsController {
  override fun restartServer(serverId: String): ResponseEntity<Unit> {
    containerService.stopContainer(serverId)
    return ResponseEntity.ok().build()
  }
}