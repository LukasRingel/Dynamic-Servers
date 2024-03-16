package net.vellity.dynamic.servers.log.viewer.agent

import net.vellity.dynamic.servers.log.viewer.agent.listener.LogLineBulkPacketListener
import net.vellity.dynamic.servers.log.viewer.agent.listener.LogLinePacketListener
import net.vellity.dynamic.servers.log.viewer.agent.stream.LogStream
import net.vellity.dynamic.servers.log.viewer.view.live.LiveViewSessionService
import net.vellity.dynamic.servers.protocol.ConnectedClient
import net.vellity.dynamic.servers.protocol.logger.NetworkingLogger
import net.vellity.dynamic.servers.protocol.packets.livelog.LogSessionClosePacket
import net.vellity.dynamic.servers.protocol.packets.livelog.LogSessionEstablishPacket
import net.vellity.dynamic.servers.protocol.server.NettyProtocolServer
import net.vellity.dynamic.servers.protocol.server.ProtocolServer
import org.springframework.stereotype.Service

@Service
class NettyAgentService(private val liveViewSessionService: LiveViewSessionService) : AgentService {
  private val protocolServer: ProtocolServer = NettyProtocolServer(
    AgentProtocolServerConfiguration(),
    logger,
    "Log-Viewer"
  )

  private val executorAgents: MutableMap<String, ConnectedClient> = mutableMapOf()
  private val streams: MutableList<LogStream> = mutableListOf()

  init {
    protocolServer.addClientConnectedHook { client ->
      executorAgents[client.name] = client
      logger.info("Agent connected: ${client.name}")
    }
    protocolServer.addClientDisconnectedHook { client ->
      executorAgents.remove(client.name)
      logger.info("Agent disconnected: ${client.name}")
    }
    protocolServer.registerListener(LogLinePacketListener(liveViewSessionService, this))
    protocolServer.registerListener(LogLineBulkPacketListener(liveViewSessionService))
    val thread = Thread { protocolServer.start() }
    thread.name = "Agent-Server"
    thread.start()
  }

  override fun isAgentConnected(executor: String): Boolean =
    executorAgents.containsKey(executor)

  override fun requestLiveLogFromAgent(executor: String, serverId: String, consumer: (String) -> Unit) {
    if (!isAgentConnected(executor)) {
      logger.error("Agent not connected: $executor")
      return
    }

    if (streams.any { stream -> stream.serverId == serverId }) {
      val logStream = streams.find { stream -> stream.serverId == serverId }
      logStream?.sessions?.add(executor)
      logStream?.logLines?.forEach { line -> consumer.invoke(line) }
      return
    }

    val connectedClient = executorAgents[executor]
    connectedClient?.sendPacket(LogSessionEstablishPacket().apply {
      this.serverId = serverId
    })
    streams.add(LogStream(serverId))
    logger.info("Requesting live log for serverId: $serverId")
  }

  override fun stopLiveLogFromAgent(executor: String, serverId: String) {
    if (!isAgentConnected(executor)) {
      return
    }

    val stream = streams.find { stream -> stream.serverId == serverId }
    stream?.sessions?.remove(executor)

    if (stream?.sessions?.isNotEmpty() == true) {
      return
    }

    streams.remove(stream)
    val connectedClient = executorAgents[executor]
    connectedClient?.sendPacket(LogSessionClosePacket().apply {
      this.serverId = serverId
    })
  }

  override fun addLineToStream(serverId: String, line: String) {
    streams.find { stream -> stream.serverId == serverId }?.logLines?.add(line)
  }

  companion object {
    private val logger: NetworkingLogger = SpringProtocolLogger()
  }
}