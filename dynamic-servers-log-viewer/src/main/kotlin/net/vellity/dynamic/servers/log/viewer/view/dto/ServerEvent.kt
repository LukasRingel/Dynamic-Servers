package net.vellity.dynamic.servers.log.viewer.view.dto

data class ServerEvent(
  val name: String,
  val timestamp: Long,
  val metaData: Map<String, String>
)
