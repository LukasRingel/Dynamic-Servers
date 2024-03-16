package net.vellity.dynamic.servers.protocol.packets.livelog

import net.vellity.dynamic.servers.protocol.packet.Packet
import net.vellity.dynamic.servers.protocol.packet.PacketBuffer

class LogLinePacket : Packet() {
  var serverId: String = ""
  var line: String = ""

  override fun read(buffer: PacketBuffer) {
    serverId = buffer.readString()
    line = buffer.readString()
  }

  override fun write(buffer: PacketBuffer) {
    buffer.writeString(serverId)
    buffer.writeString(line)
  }
}