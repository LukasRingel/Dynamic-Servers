package net.vellity.dynamic.servers.server.registry.rest.dto

import java.util.*

data class AddTagToServerDto(
  val serverId: UUID,
  val tag: String
)