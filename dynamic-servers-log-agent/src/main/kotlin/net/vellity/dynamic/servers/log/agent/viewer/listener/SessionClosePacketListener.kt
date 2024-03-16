package net.vellity.dynamic.servers.log.agent.viewer.listener

import net.vellity.dynamic.servers.log.agent.viewer.LogViewerConnection
import net.vellity.dynamic.servers.protocol.ConnectedClient
import net.vellity.dynamic.servers.protocol.packet.listener.PacketListener
import net.vellity.dynamic.servers.protocol.packets.livelog.LogSessionClosePacket

class SessionClosePacketListener(private val logViewerConnection: LogViewerConnection) :
  PacketListener<LogSessionClosePacket> {
  override fun receive(packet: LogSessionClosePacket, sender: ConnectedClient) {
    logViewerConnection.closeSession(packet.serverId)
  }
}