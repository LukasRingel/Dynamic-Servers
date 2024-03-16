package net.vellity.dynamic.servers.server.registry.rest.dto

import net.vellity.dynamic.servers.server.registry.registry.RegisteredServer
import java.util.UUID

data class CreateServerDto(
  val serverId: UUID,
  val template: String,
  val port: Int,
  val hostname: String,
  val tags: List<String>,
  val additionalEnvironmentVars: Map<String, String>
) {
  companion object {
    fun toEntity(dto: CreateServerDto, sourceName: String): RegisteredServer {
      return RegisteredServer(
        id = dto.serverId,
        template = dto.template,
        executorId = sourceName,
        port = dto.port,
        hostname = dto.hostname,
        tags = dto.tags.toMutableList(),
        additionalEnvironmentVars = dto.additionalEnvironmentVars
      )
    }
  }
}