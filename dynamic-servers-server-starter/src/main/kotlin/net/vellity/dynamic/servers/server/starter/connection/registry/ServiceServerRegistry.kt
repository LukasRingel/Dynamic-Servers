package net.vellity.dynamic.servers.server.starter.connection.registry

import net.vellity.dynamic.servers.common.http.server.environmentOrDefault
import net.vellity.dynamic.servers.server.registry.http.client.ServerRegistryClient
import net.vellity.dynamic.servers.server.registry.http.client.V1ServerRegistryClient
import org.springframework.stereotype.Component

@Component
class ServiceServerRegistry : ServerRegistry {
  private val serverRegistryClient: ServerRegistryClient = V1ServerRegistryClient(
    hostname = environmentOrDefault("SERVER_REGISTRY_HOSTNAME", "http://localhost:8083"),
    apiKey = environmentOrDefault("SERVER_REGISTRY_API_KEY", "default-api-key"),
    sourceName = environmentOrDefault("SERVER_STARTER_NAME", "local")
  )

  override fun registerServer(serverId: String, template: String, port: Int, hostname: String, tags: List<String>, additionalEnvironmentVars: Map<String, String>) =
    serverRegistryClient.createServer(serverId, template, port, hostname, tags, additionalEnvironmentVars)

  override fun hostname(): String = environmentOrDefault(
    "SERVER_REGISTRY_HOSTNAME",
    "http://localhost:8083"
  )

  override fun apiKey(): String = environmentOrDefault(
    "SERVER_REGISTRY_API_KEY",
    "default-api-key"
  )
}