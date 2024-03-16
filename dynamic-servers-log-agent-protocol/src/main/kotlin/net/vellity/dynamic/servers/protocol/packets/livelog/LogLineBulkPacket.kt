package net.vellity.dynamic.servers.protocol.packets.livelog

import net.vellity.dynamic.servers.protocol.packet.Packet
import net.vellity.dynamic.servers.protocol.packet.PacketBuffer

class LogLineBulkPacket : Packet() {
  var serverId: String = ""
  var lines: List<ByteArray> = emptyList()

  override fun read(buffer: PacketBuffer) {
    serverId = buffer.readString()
    val size = buffer.readVarInt()
    lines = (0 until size).map { buffer.readByteArray() }
  }

  override fun write(buffer: PacketBuffer) {
    buffer.writeString(serverId)
    buffer.writeVarInt(lines.size)
    lines.forEach { buffer.writeByteArray(it) }
  }
}