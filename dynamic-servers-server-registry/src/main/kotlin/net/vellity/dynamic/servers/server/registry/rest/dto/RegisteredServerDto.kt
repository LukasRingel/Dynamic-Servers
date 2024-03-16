package net.vellity.dynamic.servers.server.registry.rest.dto

import net.vellity.dynamic.servers.server.registry.registry.RegisteredServer
import net.vellity.dynamic.servers.server.registry.registry.ServerStatus
import java.util.*

data class RegisteredServerDto(
  val id: UUID,
  val template: String,
  val executorId: String,
  val port: Int,
  val hostname: String,
  var status: ServerStatus,
  val createdAt: Long,
  val tags: List<String>,
  val additionalEnvironmentVars: Map<String, String>
) {
  companion object {
    fun fromEntity(entity: RegisteredServer): RegisteredServerDto {
      return RegisteredServerDto(
        id = entity.id,
        template = entity.template,
        executorId = entity.executorId,
        port = entity.port,
        hostname = entity.hostname,
        status = entity.status,
        createdAt = entity.createdAt.toEpochMilli(),
        tags = entity.tags,
        additionalEnvironmentVars = entity.additionalEnvironmentVars
      )
    }
  }
}