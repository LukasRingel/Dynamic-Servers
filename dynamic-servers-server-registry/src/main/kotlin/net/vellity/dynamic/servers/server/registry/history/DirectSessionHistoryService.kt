package net.vellity.dynamic.servers.server.registry.history

import net.vellity.dynamic.servers.server.registry.history.update.Update
import net.vellity.dynamic.servers.server.registry.registry.RegisteredServer
import org.springframework.stereotype.Service
import java.util.*

@Service
class DirectSessionHistoryService(private val sessionHistoryRepository: SessionHistoryRepository) :
  SessionHistoryService {
  override fun createSessionHistoryForRegisteredServer(registeredServer: RegisteredServer): ServerSessionHistory {
    val serverSessionHistory = ServerSessionHistory()
    serverSessionHistory.id = registeredServer.id.toString()
    serverSessionHistory.template = registeredServer.template
    serverSessionHistory.executorId = registeredServer.executorId
    serverSessionHistory.createdAt = registeredServer.createdAt
    serverSessionHistory.updates = listOf()
    return sessionHistoryRepository.save(serverSessionHistory)
  }

  override fun addUpdateToSessionHistory(serverId: UUID, update: Update) {
    val sessionHistory = sessionHistoryRepository.findById(serverId.toString()).get()
    val updates = sessionHistory.updates!!.toMutableList()
    updates.add(update)
    sessionHistory.updates = updates
    sessionHistoryRepository.save(sessionHistory)
  }
}