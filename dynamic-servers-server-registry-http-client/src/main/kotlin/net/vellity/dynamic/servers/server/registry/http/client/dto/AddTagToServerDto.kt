package net.vellity.dynamic.servers.server.registry.http.client.dto

data class AddTagToServerDto(
  val serverId: String,
  val tag: String
)