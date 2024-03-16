package net.vellity.dynamic.servers.server.registry.history.rest

import net.vellity.dynamic.servers.server.registry.history.update.UpdateType

data class UpdateDto(
  val type: UpdateType,
  val timestamp: Long,
  val metaData: Map<String, String>
)