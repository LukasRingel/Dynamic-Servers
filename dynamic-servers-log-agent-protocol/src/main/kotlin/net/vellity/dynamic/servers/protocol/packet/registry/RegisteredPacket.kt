package net.vellity.dynamic.servers.protocol.packet.registry

import net.vellity.dynamic.servers.protocol.ConnectedClient
import net.vellity.dynamic.servers.protocol.packet.listener.PacketListener
import net.vellity.dynamic.servers.protocol.packet.Packet
import java.lang.reflect.Constructor
import java.util.concurrent.atomic.AtomicInteger

class RegisteredPacket(private val constructor: Constructor<out Packet>, private val counter: AtomicInteger) {

  private val listeners = mutableSetOf<PacketListener<Packet>>()

  fun create(): Packet {
    nextId()
    return constructor.newInstance()
  }

  private fun nextId(): Int {
    return counter.incrementAndGet()
  }

  fun registerListener(listener: PacketListener<Packet>) {
    listeners.add(listener)
  }

  fun callListeners(packet: Packet, sender: ConnectedClient) {
    listeners.forEach {
      it.receive(packet, sender)
    }
  }

  companion object {
    fun <T : Packet> create(packet: Class<T>): RegisteredPacket {
      val constructor = packet.getDeclaredConstructor()
      constructor.isAccessible = true
      return RegisteredPacket(constructor, AtomicInteger(0))
    }
  }
}