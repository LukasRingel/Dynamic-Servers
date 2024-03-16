package net.vellity.dynamic.servers.log.viewer

import net.vellity.dynamic.servers.common.http.server.environmentOrDefault
import net.vellity.dynamic.servers.server.registry.http.client.ServerRegistryClient
import net.vellity.dynamic.servers.server.registry.http.client.V1ServerRegistryClient
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service

@Service
class DynamicServersLogViewerBeans {
  @Bean
  fun serverRegistryClient(): ServerRegistryClient = V1ServerRegistryClient(
    hostname = environmentOrDefault("DYNAMIC_SERVERS_SERVER_REGISTRY_HOSTNAME", "http://localhost:8083"),
    apiKey = environmentOrDefault("DYNAMIC_SERVERS_SERVER_REGISTRY_API_KEY", "default-api-key"),
    sourceName = "log-viewer"
  )
}