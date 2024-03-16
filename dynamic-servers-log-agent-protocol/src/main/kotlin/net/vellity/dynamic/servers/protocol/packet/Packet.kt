package net.vellity.dynamic.servers.protocol.packet

abstract class Packet {
  /**
   * Override this method to read the packet data from the buffer
   */
  abstract fun read(buffer: PacketBuffer)

  /**
   * Override this method to write the packet data to the buffer
   */
  abstract fun write(buffer: PacketBuffer)
}