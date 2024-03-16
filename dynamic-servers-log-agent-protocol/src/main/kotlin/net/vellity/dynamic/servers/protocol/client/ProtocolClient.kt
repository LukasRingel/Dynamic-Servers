package net.vellity.dynamic.servers.protocol.client

import net.vellity.dynamic.servers.protocol.packet.Packet
import net.vellity.dynamic.servers.protocol.packet.listener.PacketListener

interface ProtocolClient {
  fun connect()

  fun sendPacket(packet: Packet)

  fun state(): ClientState

  fun registerListener(listener: PacketListener<*>)
}