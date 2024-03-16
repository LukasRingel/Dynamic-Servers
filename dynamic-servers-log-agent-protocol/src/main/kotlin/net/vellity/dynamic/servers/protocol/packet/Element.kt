package net.vellity.dynamic.servers.protocol.packet

interface Element {
  /**
   * Override this method to read the element from the buffer
   */
  fun read(buffer: PacketBuffer)

  /**
   * Override this method to write the element to the buffer
   */
  fun write(buffer: PacketBuffer)
}