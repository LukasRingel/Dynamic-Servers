package net.vellity.dynamic.servers.log.viewer.agent.listener

import net.vellity.dynamic.servers.log.viewer.view.live.LiveViewSessionService
import net.vellity.dynamic.servers.protocol.ConnectedClient
import net.vellity.dynamic.servers.protocol.packet.listener.PacketListener
import net.vellity.dynamic.servers.protocol.packets.livelog.LogLineBulkPacket

class LogLineBulkPacketListener(private val sessionService: LiveViewSessionService) :
  PacketListener<LogLineBulkPacket> {
  override fun receive(packet: LogLineBulkPacket, sender: ConnectedClient) {
    sessionService.getSessionsWithServerId(packet.serverId).forEach {
      it.consumer?.invoke(packet.lines.joinToString { "\n" })
    }
  }
}