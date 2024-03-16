package net.vellity.dynamic.servers.server.registry.http.client

import net.vellity.dynamic.servers.server.registry.http.client.dto.RegisteredServerDto
import net.vellity.dynamic.servers.server.registry.http.client.dto.ServerHistoryDto
import net.vellity.dynamic.servers.server.registry.http.client.dto.ServerStatus

interface ServerRegistryClient {
  fun createServer(
    serverId: String,
    template: String,
    port: Int,
    hostname: String,
    tags: List<String>,
    additionalEnvironmentVars: Map<String, String>
  )

  fun deleteServer(serverId: String, reason: String)

  fun updateServerStatus(serverId: String, status: ServerStatus)

  fun addTagsToServer(serverId: String, tag: String)

  fun allServers(): List<RegisteredServerDto>

  fun allServersOfTemplate(template: String): List<RegisteredServerDto>

  fun allServersOnExecutor(executor: String): List<RegisteredServerDto>

  fun allServersWithoutTag(tags: String): List<RegisteredServerDto>

  fun getServerHistory(serverId: String): ServerHistoryDto

  fun findHistories(
    limit: Int,
    minDate: Long,
    maxDate: Long,
    template: String = "",
    executor: String = ""
  ): List<ServerHistoryDto>
}