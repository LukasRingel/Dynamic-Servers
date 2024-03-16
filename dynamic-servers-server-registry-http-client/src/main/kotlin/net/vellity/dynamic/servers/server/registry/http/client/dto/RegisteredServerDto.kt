package net.vellity.dynamic.servers.server.registry.http.client.dto

import java.util.*

data class RegisteredServerDto(
  val id: UUID,
  val template: String,
  val executorId: String,
  val port: Int,
  val hostname: String,
  var status: ServerStatus,
  val createdAt: Long,
  val tags: List<String>,
  val additionalEnvironmentVars: Map<String, String>
)