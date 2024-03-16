package net.vellity.dynamic.servers.log.agent.viewer

interface LogViewerConnection {
  fun createSession(serverId: String, consumer: (String) -> Unit)

  fun closeSession(serverId: String)
}