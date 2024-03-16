package net.vellity.dynamic.servers.protocol

import io.netty.channel.Channel
import net.vellity.dynamic.servers.protocol.client.ClientState
import net.vellity.dynamic.servers.protocol.packet.Packet

class ConnectedClient(var name: String, val channel: Channel) {
  var lastHeartbeat: Long = System.currentTimeMillis()
  var state = ClientState.AUTHENTICATING

  fun sendPacket(packet: Packet) {
    channel.writeAndFlush(packet)
  }

  fun closeConnection() {
    channel.close()
  }
}