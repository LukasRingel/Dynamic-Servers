package net.vellity.dynamic.servers.server.registry.registry

import java.util.*

interface ServerRegistry {
  fun registerServer(server: RegisteredServer)

  fun unregisterServer(serverId: UUID)

  fun getServer(serverId: UUID): RegisteredServer?

  fun allServers(): List<RegisteredServer>

  fun allServersOfTemplate(template: String): List<RegisteredServer>

  fun allServersOnExecutor(executor: String): List<RegisteredServer>

  fun allServersWithoutTags(tags: List<String>): List<RegisteredServer>
}