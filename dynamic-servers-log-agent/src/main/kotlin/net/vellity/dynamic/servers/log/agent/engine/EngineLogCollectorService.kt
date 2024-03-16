package net.vellity.dynamic.servers.log.agent.engine

interface EngineLogCollectorService {
  fun streamLogsForServer(serverId: String, consumer: (String) -> Unit)

  fun stopStreamingLogsForServer(serverId: String)
}