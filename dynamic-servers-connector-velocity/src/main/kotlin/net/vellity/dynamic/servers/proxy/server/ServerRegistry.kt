package net.vellity.dynamic.servers.proxy.server

import net.vellity.dynamic.servers.server.registry.http.client.dto.RegisteredServerDto
import net.vellity.dynamic.servers.server.registry.http.client.dto.ServerStatus

interface ServerRegistry {
  fun updateServerStatus(serverId: String, status: ServerStatus)

  fun getServersWithoutTag(tag: String): List<RegisteredServerDto>
}