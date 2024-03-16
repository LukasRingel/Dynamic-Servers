package net.vellity.dynamic.servers.log.viewer.view.live

import org.springframework.stereotype.Service

@Service
class LocalLiveViewSessionService : LiveViewSessionService {
  private val sessions = mutableMapOf<String, LiveViewSession>()

  override fun createSession(
    serverId: String,
    executor: String,
    webSocketId: String,
    consumer: (String) -> Unit
  ): LiveViewSession {
    val session = LiveViewSession(webSocketId, serverId, executor, consumer)
    sessions[webSocketId] = session
    return session
  }

  override fun getSession(id: String): LiveViewSession? = sessions[id]

  override fun getSessionsWithServerId(serverId: String): List<LiveViewSession> =
    sessions.values.filter { it.serverId == serverId }

  override fun removeSession(id: String) {
    sessions.remove(id)
  }
}