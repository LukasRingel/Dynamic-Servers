package net.vellity.dynamic.servers.log.viewer.agent

import net.vellity.dynamic.servers.common.http.server.environmentOrDefault
import net.vellity.dynamic.servers.protocol.server.ProtocolServerConfiguration

class AgentProtocolServerConfiguration : ProtocolServerConfiguration {
  override fun port(): Int =
    environmentOrDefault("AGENT_PROTOCOL_SERVER_PORT", "9000").toInt()

  override fun password(): String =
    environmentOrDefault("AGENT_PROTOCOL_SERVER_PASSWORD", "default-password")

  override fun workerThreads(): Int =
    environmentOrDefault("AGENT_PROTOCOL_SERVER_WORKER_THREADS", "16").toInt()
}