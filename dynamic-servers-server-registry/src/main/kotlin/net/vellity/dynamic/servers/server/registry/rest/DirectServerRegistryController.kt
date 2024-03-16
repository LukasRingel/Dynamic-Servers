package net.vellity.dynamic.servers.server.registry.rest

import net.vellity.dynamic.servers.server.registry.history.SessionHistoryService
import net.vellity.dynamic.servers.server.registry.history.update.Updates
import net.vellity.dynamic.servers.server.registry.registry.RegisteredServer
import net.vellity.dynamic.servers.server.registry.registry.ServerRegistry
import net.vellity.dynamic.servers.server.registry.registry.ServerStatus
import net.vellity.dynamic.servers.server.registry.rest.dto.AddTagToServerDto
import net.vellity.dynamic.servers.server.registry.rest.dto.CreateServerDto
import net.vellity.dynamic.servers.server.registry.rest.dto.RegisteredServerDto
import net.vellity.dynamic.servers.server.registry.rest.dto.UpdateServerStatusDto
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class DirectServerRegistryController(
  private val serverRegistry: ServerRegistry,
  private val sessionHistoryService: SessionHistoryService
) : ServerRegistryController {
  override fun createServer(sourceName: String, createServerDto: CreateServerDto): ResponseEntity<Unit> {
    val registeredServer = CreateServerDto.toEntity(createServerDto, sourceName)
    serverRegistry.registerServer(registeredServer)
    sessionHistoryService.createSessionHistoryForRegisteredServer(registeredServer)
    sessionHistoryService.addUpdateToSessionHistory(registeredServer.id, Updates.created())
    logger.info(
      "$sourceName registered server ${registeredServer.id} of type ${registeredServer.template} on " +
        "${registeredServer.hostname}:${registeredServer.port}"
    )
    return ResponseEntity.ok().build()
  }

  override fun deleteServer(sourceName: String, serverId: String, reason: String): ResponseEntity<Unit> {
    serverRegistry.getServer(UUID.fromString(serverId)) ?: return ResponseEntity.notFound().build()
    serverRegistry.unregisterServer(UUID.fromString(serverId))
    sessionHistoryService.addUpdateToSessionHistory(UUID.fromString(serverId), Updates.removed())
    logger.info("$sourceName deleted server ${serverId}(${reason})")
    return ResponseEntity.ok().build()
  }

  override fun updateServerStatus(
    sourceName: String,
    updateServerStatusDto: UpdateServerStatusDto
  ): ResponseEntity<Unit> {
    val server = serverRegistry.getServer(updateServerStatusDto.serverId)
    if (server == null) {
      logger.warn("$sourceName tried to update status of non-existent server ${updateServerStatusDto.serverId}")
      return ResponseEntity.notFound().build()
    }
    server.status = updateServerStatusDto.status
    sessionHistoryService.addUpdateToSessionHistory(
      updateServerStatusDto.serverId,
      Updates.forStatusUpdate(updateServerStatusDto.status)
    )

    logger.info("$sourceName updated status of ${updateServerStatusDto.serverId} to ${updateServerStatusDto.status}")

    // If the server is ready to delete, we should update it to that status
    if (ServerStatus.READY_TO_DELETE.ordinal - 1 == server.status.ordinal) {
      updateServerStatus(
        "automatic",
        UpdateServerStatusDto(updateServerStatusDto.serverId, ServerStatus.READY_TO_DELETE)
      )
    }

    return ResponseEntity.ok().build()
  }

  override fun addTagsToServer(sourceName: String, addTagToServerDto: AddTagToServerDto): ResponseEntity<Unit> {
    val server = serverRegistry.getServer(addTagToServerDto.serverId)
    if (server == null) {
      logger.warn("$sourceName tried to add tags to non-existent server ${addTagToServerDto.serverId}")
      return ResponseEntity.notFound().build()
    }
    if (server.tags.contains(addTagToServerDto.tag)) {
      return ResponseEntity.ok().build()
    }
    server.tags.add(addTagToServerDto.tag)
    sessionHistoryService.addUpdateToSessionHistory(
      addTagToServerDto.serverId,
      Updates.tagsElementAdded(addTagToServerDto.tag, sourceName)
    )
    logger.info("$sourceName added tag ${addTagToServerDto.tag} to server ${addTagToServerDto.serverId}")
    return ResponseEntity.ok().build()
  }

  override fun allServers(): ResponseEntity<List<RegisteredServerDto>> {
    return ResponseEntity.ok(
      serverRegistry.allServers()
        .map { RegisteredServerDto.fromEntity(it) }
    )
  }

  override fun allServersOfTemplate(template: String): ResponseEntity<List<RegisteredServerDto>> {
    return ResponseEntity.ok(
      serverRegistry.allServersOfTemplate(template)
        .map { RegisteredServerDto.fromEntity(it) }
    )
  }

  override fun allServersOnExecutor(executor: String): ResponseEntity<List<RegisteredServerDto>> {
    return ResponseEntity.ok(
      serverRegistry.allServersOnExecutor(executor)
        .map { RegisteredServerDto.fromEntity(it) }
    )
  }

  override fun allServersWithoutTag(tags: String): ResponseEntity<List<RegisteredServerDto>> {
    return ResponseEntity.ok(
      serverRegistry.allServersWithoutTags(listOf(tags))
        .map { RegisteredServerDto.fromEntity(it) }
    )
  }

  companion object {
    private val logger = LoggerFactory.getLogger(ServerRegistryController::class.java)
  }
}