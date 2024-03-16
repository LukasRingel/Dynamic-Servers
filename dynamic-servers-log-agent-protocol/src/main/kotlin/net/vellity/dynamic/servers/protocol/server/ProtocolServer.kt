package net.vellity.dynamic.servers.protocol.server

import net.vellity.dynamic.servers.protocol.ConnectedClient
import net.vellity.dynamic.servers.protocol.packet.Packet
import net.vellity.dynamic.servers.protocol.packet.listener.PacketListener
import java.util.function.Predicate

interface ProtocolServer {
  fun start()

  fun stop()

  fun isRunning(): Boolean

  fun broadcastPacket(packet: Packet)

  fun broadcastPacket(packet: Packet, filter: Predicate<ConnectedClient>)

  fun registerListener(listener: PacketListener<*>)

  fun addClientConnectedHook(hook: (ConnectedClient) -> Unit)

  fun addClientDisconnectedHook(hook: (ConnectedClient) -> Unit)
}