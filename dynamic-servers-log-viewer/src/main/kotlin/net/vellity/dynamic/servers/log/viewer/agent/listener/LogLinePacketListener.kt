package net.vellity.dynamic.servers.log.viewer.agent.listener

import net.vellity.dynamic.servers.log.viewer.agent.AgentService
import net.vellity.dynamic.servers.log.viewer.view.live.LiveViewSessionService
import net.vellity.dynamic.servers.protocol.ConnectedClient
import net.vellity.dynamic.servers.protocol.packet.listener.PacketListener
import net.vellity.dynamic.servers.protocol.packets.livelog.LogLinePacket

class LogLinePacketListener(
  private val sessionService: LiveViewSessionService,
  private val agentService: AgentService
) : PacketListener<LogLinePacket> {
  override fun receive(packet: LogLinePacket, sender: ConnectedClient) {
    sessionService.getSessionsWithServerId(packet.serverId).forEach {
      it.consumer?.invoke(packet.line)
      agentService.addLineToStream(packet.serverId, packet.line)
    }
  }
}