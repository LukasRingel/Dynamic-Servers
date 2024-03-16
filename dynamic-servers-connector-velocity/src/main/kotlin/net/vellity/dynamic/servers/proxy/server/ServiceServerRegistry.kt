package net.vellity.dynamic.servers.proxy.server

import net.vellity.dynamic.servers.proxy.containerId
import net.vellity.dynamic.servers.proxy.environmentOrDefault
import net.vellity.dynamic.servers.server.registry.http.client.ServerRegistryClient
import net.vellity.dynamic.servers.server.registry.http.client.V1ServerRegistryClient
import net.vellity.dynamic.servers.server.registry.http.client.dto.RegisteredServerDto
import net.vellity.dynamic.servers.server.registry.http.client.dto.ServerStatus

class ServiceServerRegistry : ServerRegistry {
  private val serverRegistryClient: ServerRegistryClient = V1ServerRegistryClient(
    hostname = environmentOrDefault("DYNAMIC_SERVERS_SERVER_REGISTRY_HOSTNAME", "http://localhost:8083"),
    apiKey = environmentOrDefault("DYNAMIC_SERVERS_SERVER_REGISTRY_API_KEY", "default-api-key"),
    sourceName = containerId()
  )

  override fun updateServerStatus(serverId: String, status: ServerStatus) {
    serverRegistryClient.updateServerStatus(serverId, status)
  }

  override fun getServersWithoutTag(tag: String): List<RegisteredServerDto> {
    return serverRegistryClient.allServersWithoutTag(tag)
  }
}