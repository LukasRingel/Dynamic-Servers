package net.vellity.dynamic.servers.server.registry.rest.dto

import net.vellity.dynamic.servers.server.registry.registry.ServerStatus
import java.util.UUID

data class UpdateServerStatusDto(
  val serverId: UUID,
  val status: ServerStatus
)