package net.vellity.dynamic.servers.log.viewer.view.dto

data class LiveViewDataDto(
  val session: String,
  val serverId: String,
  val ready: Boolean,
  val executor: String,
  val startedAt: Long,
  val websocketUrl: String
)