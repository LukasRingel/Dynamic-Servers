package net.vellity.dynamic.servers.log.agent.viewer.listener

import net.vellity.dynamic.servers.log.agent.viewer.LogViewerConnection
import net.vellity.dynamic.servers.protocol.ConnectedClient
import net.vellity.dynamic.servers.protocol.packet.listener.PacketListener
import net.vellity.dynamic.servers.protocol.packets.livelog.LogLinePacket
import net.vellity.dynamic.servers.protocol.packets.livelog.LogSessionEstablishPacket

class SessionEstablishPacketListener(private val logViewerConnection: LogViewerConnection) :
  PacketListener<LogSessionEstablishPacket> {
  override fun receive(packet: LogSessionEstablishPacket, sender: ConnectedClient) {
    logViewerConnection.createSession(packet.serverId) {
      sender.sendPacket(LogLinePacket().apply {
        this.serverId = packet.serverId
        this.line = it
      })
    }
  }
}