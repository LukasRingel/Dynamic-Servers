package net.vellity.dynamic.servers.protocol.packet.listener

import net.vellity.dynamic.servers.protocol.ConnectedClient
import net.vellity.dynamic.servers.protocol.packet.Packet

interface PacketListener<in T> where T : Packet {
  fun receive(packet: T, sender: ConnectedClient)
}