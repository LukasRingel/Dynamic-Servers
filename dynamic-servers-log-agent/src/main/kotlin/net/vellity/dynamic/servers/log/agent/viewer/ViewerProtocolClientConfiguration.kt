package net.vellity.dynamic.servers.log.agent.viewer

import net.vellity.dynamic.servers.common.http.server.environmentOrDefault
import net.vellity.dynamic.servers.protocol.client.ProtocolClientConfiguration
import net.vellity.dynamic.servers.protocol.server.ProtocolServerConfiguration

class ViewerProtocolClientConfiguration : ProtocolClientConfiguration {
  override fun hostname(): String =
    environmentOrDefault("VIEWER_PROTOCOL_CLIENT_HOSTNAME", "localhost")

  override fun port(): Int =
    environmentOrDefault("VIEWER_PROTOCOL_CLIENT_PORT", "9000").toInt()

  override fun password(): String =
    environmentOrDefault("VIEWER_PROTOCOL_CLIENT_PASSWORD", "default-password")

  override fun threads(): Int =
    environmentOrDefault("VIEWER_PROTOCOL_CLIENT_WORKER_THREADS", "4").toInt()
}