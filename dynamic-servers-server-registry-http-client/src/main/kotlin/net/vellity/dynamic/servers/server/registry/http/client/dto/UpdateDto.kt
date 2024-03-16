package net.vellity.dynamic.servers.server.registry.http.client.dto

data class UpdateDto(
  val type: UpdateType,
  val timestamp: Long,
  val metaData: Map<String, String>
)