package net.vellity.dynamic.servers.operations.connection.registry

import net.vellity.dynamic.servers.server.registry.http.client.dto.RegisteredServerDto
import net.vellity.dynamic.servers.server.registry.http.client.dto.ServerStatus
import retrofit2.Call

interface ServerRegistry {
  fun getServersOnExecutor(executor: String): List<RegisteredServerDto>

  fun updateServerStatus(serverId: String, status: ServerStatus)

  fun addTagsToServer(serverId: String, tag: String)

  fun removeServer(serverId: String, reason: String)

  fun allServersOfTemplate(template: String): List<RegisteredServerDto>
}