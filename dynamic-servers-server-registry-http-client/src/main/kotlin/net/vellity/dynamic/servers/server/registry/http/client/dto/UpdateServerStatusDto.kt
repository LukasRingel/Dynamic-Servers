package net.vellity.dynamic.servers.server.registry.http.client.dto

data class UpdateServerStatusDto(
  val serverId: String,
  val status: ServerStatus
)