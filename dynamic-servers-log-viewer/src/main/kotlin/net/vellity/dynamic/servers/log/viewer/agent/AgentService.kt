package net.vellity.dynamic.servers.log.viewer.agent

interface AgentService {
  fun isAgentConnected(executor: String): Boolean

  fun requestLiveLogFromAgent(executor: String, serverId: String, consumer: (String) -> Unit)

  fun stopLiveLogFromAgent(executor: String, serverId: String)

  fun addLineToStream(serverId: String, line: String)
}