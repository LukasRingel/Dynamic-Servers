package net.vellity.dynamic.servers.server.registry.history.rest

data class ServerHistoryDto(
  val serverId: String,
  val template: String,
  val executorId: String,
  val createdAt: Long,
  val updates: List<UpdateDto>
)
