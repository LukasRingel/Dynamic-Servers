package net.vellity.dynamic.servers.protocol.packet.listener

import net.vellity.dynamic.servers.protocol.ConnectedClient
import net.vellity.dynamic.servers.protocol.packet.Packet

class WrappedPacketListener<in T>(private val listener: (T, ConnectedClient) -> Unit) :
  PacketListener<T> where T : Packet {
  override fun receive(packet: T, sender: ConnectedClient) {
    listener.invoke(packet, sender)
  }
}