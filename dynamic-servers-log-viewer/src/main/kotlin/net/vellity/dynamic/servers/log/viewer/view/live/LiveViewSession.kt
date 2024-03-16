package net.vellity.dynamic.servers.log.viewer.view.live

data class LiveViewSession(
  val id: String,
  val serverId: String,
  val executor: String,
  var consumer: ((String) -> Unit)? = null
)