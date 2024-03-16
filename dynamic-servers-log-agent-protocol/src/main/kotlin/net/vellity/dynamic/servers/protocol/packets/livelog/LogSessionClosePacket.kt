package net.vellity.dynamic.servers.protocol.packets.livelog

import net.vellity.dynamic.servers.protocol.packet.Packet
import net.vellity.dynamic.servers.protocol.packet.PacketBuffer

class LogSessionClosePacket : Packet() {
  var serverId: String = ""

  override fun read(buffer: PacketBuffer) {
    serverId = buffer.readString()
  }

  override fun write(buffer: PacketBuffer) {
    buffer.writeString(serverId)
  }
}