package net.vellity.dynamic.servers.protocol.packets.connection

import net.vellity.dynamic.servers.protocol.packet.Packet
import net.vellity.dynamic.servers.protocol.packet.PacketBuffer

class RemoteShuttingDownPacket: Packet() {
  override fun read(buffer: PacketBuffer) {
  }

  override fun write(buffer: PacketBuffer) {
  }
}