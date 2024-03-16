package net.vellity.dynamic.servers.log.agent.viewer

import net.vellity.dynamic.servers.common.http.server.environmentOrDefault
import net.vellity.dynamic.servers.log.agent.engine.EngineLogCollectorService
import net.vellity.dynamic.servers.log.agent.viewer.listener.SessionClosePacketListener
import net.vellity.dynamic.servers.log.agent.viewer.listener.SessionEstablishPacketListener
import net.vellity.dynamic.servers.protocol.client.NettyProtocolClient
import net.vellity.dynamic.servers.protocol.client.ProtocolClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ProtocolLogViewerConnection(private val engineLogCollectorService: EngineLogCollectorService) : LogViewerConnection {
  private val protocolClient: ProtocolClient = NettyProtocolClient(
    "log-viewer",
    environmentOrDefault("SERVER_STARTER_NAME", "local"),
    ViewerProtocolClientConfiguration(),
    SpringProtocolLogger()
  )

  init {
    protocolClient.registerListener(SessionEstablishPacketListener(this))
    protocolClient.registerListener(SessionClosePacketListener(this))
    val thread = Thread { protocolClient.connect() }
    thread.name = "Viewer-Server"
    thread.start()
  }

  override fun createSession(serverId: String, consumer: (String) -> Unit) {
    engineLogCollectorService.streamLogsForServer(serverId, consumer)
    logger.info("Created session for serverId: $serverId")
  }

  override fun closeSession(serverId: String) {
    engineLogCollectorService.stopStreamingLogsForServer(serverId)
    logger.info("Closed session for serverId: $serverId")
  }

  companion object {
    private val logger = LoggerFactory.getLogger(ProtocolLogViewerConnection::class.java)
  }
}