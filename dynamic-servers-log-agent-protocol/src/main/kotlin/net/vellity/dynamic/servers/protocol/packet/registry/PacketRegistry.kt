package net.vellity.dynamic.servers.protocol.packet.registry

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
import net.vellity.dynamic.servers.protocol.ConnectedClient
import net.vellity.dynamic.servers.protocol.packet.Packet
import net.vellity.dynamic.servers.protocol.packet.listener.PacketListener
import net.vellity.dynamic.servers.protocol.packets.authentication.*
import net.vellity.dynamic.servers.protocol.packets.connection.ClientHearthBeatPacket
import net.vellity.dynamic.servers.protocol.packets.connection.RemoteShuttingDownPacket
import net.vellity.dynamic.servers.protocol.packets.livelog.LogLineBulkPacket
import net.vellity.dynamic.servers.protocol.packets.livelog.LogLinePacket
import net.vellity.dynamic.servers.protocol.packets.livelog.LogSessionClosePacket
import net.vellity.dynamic.servers.protocol.packets.livelog.LogSessionEstablishPacket
import kotlin.math.abs

object PacketRegistry {

  private val packetIdMap = Object2IntOpenHashMap<Class<out Packet>>()
  private val packetClasses = Int2ObjectOpenHashMap<Class<out Packet>>()
  private val packetConstructors = Int2ObjectOpenHashMap<RegisteredPacket>()

  /**
   * Registers a packet listener for the specified packet
   * @param packet the packet class
   * @param listener the packet listener
   */
  fun <T : Packet> registerListener(packet: Class<T>, listener: PacketListener<T>) {
    if (!packetIdMap.containsKey(packet)) {
      throw IllegalArgumentException("Unable to register listener ${listener.javaClass.simpleName} since Packet ${packet.name} is not registered")
    }
    packetConstructors[getPacketId(packet)].registerListener(listener as PacketListener<Packet>)
  }

  /**
   * Calls all packet listeners for the specified packet
   * @param packet the packet
   * @param sender the sender of the packet
   */
  fun callListeners(packet: Packet, sender: ConnectedClient) {
    packetConstructors[getPacketId(packet.javaClass)].callListeners(packet, sender)
  }

  fun registerAllPackets() {
    val classes = listOf(
      ClientTryAuthenticatePacket::class.java,
      YouAreNowAuthenticatedPacket::class.java,
      YouSentInvalidPasswordPacket::class.java,
      YouShouldAuthenticatePacket::class.java,
      YouTookToLongToAuthenticatePacket::class.java,
      ClientHearthBeatPacket::class.java,
      RemoteShuttingDownPacket::class.java,
      LogLineBulkPacket::class.java,
      LogLinePacket::class.java,
      LogSessionEstablishPacket::class.java,
      LogSessionClosePacket::class.java
    )
    for (clazz in classes) {
      registerPacket(clazz.simpleName, clazz)
    }
  }

  /**
   * Registers a packet with the specified name and class
   * @param name the name of the packet
   * @param packet the packet class
   */
  private fun registerPacket(name: String, packet: Class<out Packet>) {
    val packetId = abs(name.hashCode())

    if (packetConstructors.containsKey(packetId)) {
      return
    }

    try {
      packetConstructors.put(packetId, RegisteredPacket.create(packet))
    } catch (e: NoSuchMethodException) {
      throw IllegalArgumentException("Packet $packet does not have a default constructor!")
    }

    packetIdMap.put(packet, packetId)
    packetClasses.put(packetId, packet)
  }

  /**
   * Gets the packet id of the specified packet
   */
  fun getPacketId(packet: Class<out Packet>): Int {
    return packetIdMap.getInt(packet)
  }

  /**
   * Gets the packet class of the specified packet id
   */
  fun getPacketClass(id: Int): Class<out Packet> {
    return packetClasses.get(id)
  }

  /**
   * Checks if the specified packet is registered
   */
  fun isRegistered(packet: Class<out Packet>): Boolean {
    return packetIdMap.containsKey(packet)
  }

  /**
   * Creates a new packet with the specified id
   */
  fun createPacket(id: Int): Packet {
    return packetConstructors.get(id).create()
  }
}