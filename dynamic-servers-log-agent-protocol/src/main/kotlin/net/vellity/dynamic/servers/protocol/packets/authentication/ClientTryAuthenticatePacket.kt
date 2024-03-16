package net.vellity.dynamic.servers.protocol.packets.authentication

import net.vellity.dynamic.servers.protocol.packet.Packet
import net.vellity.dynamic.servers.protocol.packet.PacketBuffer

class ClientTryAuthenticatePacket(
  var name: String = "",
  var password: String = ""
) : Packet() {
  override fun read(buffer: PacketBuffer) {
    name = buffer.readString()
    password = buffer.readString()
  }

  override fun write(buffer: PacketBuffer) {
    buffer.writeString(name)
    buffer.writeString(password)
  }
}