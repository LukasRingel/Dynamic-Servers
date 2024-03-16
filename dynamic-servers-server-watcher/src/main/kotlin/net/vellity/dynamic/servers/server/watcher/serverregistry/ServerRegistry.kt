package net.vellity.dynamic.servers.server.watcher.serverregistry

import net.vellity.dynamic.servers.server.registry.http.client.dto.RegisteredServerDto
import net.vellity.dynamic.servers.server.registry.http.client.dto.ServerStatus

interface ServerRegistry {
  fun getContainer(containerId: String): RegisteredServerDto?

  fun getServersOnExecutor(): List<RegisteredServerDto>

  fun updateServerStatus(serverId: String, status: ServerStatus)

  fun removeServer(serverId: String, reason: String)
}