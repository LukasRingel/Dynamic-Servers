package net.vellity.dynamic.servers.operations.connection.registry

import net.vellity.dynamic.servers.common.http.server.environmentOrDefault
import net.vellity.dynamic.servers.server.registry.http.client.ServerRegistryClient
import net.vellity.dynamic.servers.server.registry.http.client.V1ServerRegistryClient
import net.vellity.dynamic.servers.server.registry.http.client.dto.RegisteredServerDto
import net.vellity.dynamic.servers.server.registry.http.client.dto.ServerStatus
import org.springframework.stereotype.Component

@Component
class ServiceServerRegistry : ServerRegistry {
  private val serverRegistryClient: ServerRegistryClient = V1ServerRegistryClient(
    hostname = environmentOrDefault("SERVER_REGISTRY_HOSTNAME", "http://localhost:8083"),
    apiKey = environmentOrDefault("SERVER_REGISTRY_API_KEY", "default-api-key"),
    sourceName = "operations"
  )

  override fun getServersOnExecutor(executor: String): List<RegisteredServerDto> {
    return serverRegistryClient.allServersOnExecutor(executor)
  }

  override fun updateServerStatus(serverId: String, status: ServerStatus) {
    serverRegistryClient.updateServerStatus(serverId, status)
  }

  override fun addTagsToServer(serverId: String, tag: String) {
    serverRegistryClient.addTagsToServer(serverId, tag)
  }

  override fun removeServer(serverId: String, reason: String) {
    serverRegistryClient.deleteServer(serverId, reason)
  }

  override fun allServersOfTemplate(template: String): List<RegisteredServerDto> {
    return serverRegistryClient.allServersOfTemplate(template)
  }
}