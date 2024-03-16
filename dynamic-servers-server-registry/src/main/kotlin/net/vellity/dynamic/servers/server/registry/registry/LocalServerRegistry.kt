package net.vellity.dynamic.servers.server.registry.registry

import org.springframework.stereotype.Component
import java.util.*

@Component
class LocalServerRegistry : ServerRegistry {
  private val servers = mutableListOf<RegisteredServer>()

  override fun registerServer(server: RegisteredServer) {
    servers.add(server)
  }

  override fun unregisterServer(serverId: UUID) {
    servers.removeIf { it.id == serverId }
  }

  override fun getServer(serverId: UUID): RegisteredServer? {
    return servers.find { it.id == serverId }
  }

  override fun allServers(): List<RegisteredServer> {
    return servers
  }

  override fun allServersOfTemplate(template: String): List<RegisteredServer> {
    return servers.filter { it.template == template }
  }

  override fun allServersOnExecutor(executor: String): List<RegisteredServer> {
    return servers.filter { it.executorId == executor }
  }

  override fun allServersWithoutTags(tags: List<String>): List<RegisteredServer> {
    return servers.filter { server -> tags.none { server.tags.contains(it) } }
  }
}