package net.vellity.dynamic.servers.server.registry.history

import net.vellity.dynamic.servers.server.registry.history.update.Update
import net.vellity.dynamic.servers.server.registry.registry.RegisteredServer
import java.util.*

interface SessionHistoryService {
  fun createSessionHistoryForRegisteredServer(registeredServer: RegisteredServer): ServerSessionHistory

  fun addUpdateToSessionHistory(serverId: UUID, update: Update)
}