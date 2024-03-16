package net.vellity.dynamic.servers.server.registry.http.client.dto

data class CreateServerDto(
  val serverId: String,
  val template: String,
  val port: Int,
  val hostname: String,
  val tags: List<String>,
  val additionalEnvironmentVars: Map<String, String>
)