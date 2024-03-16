package net.vellity.dynamic.servers.log.viewer.view.live

interface LiveViewSessionService {
  fun createSession(serverId: String, executor: String, webSocketId: String, consumer: (String) -> Unit): LiveViewSession

  fun getSession(id: String): LiveViewSession?

  fun getSessionsWithServerId(serverId: String): List<LiveViewSession>

  fun removeSession(id: String)
}