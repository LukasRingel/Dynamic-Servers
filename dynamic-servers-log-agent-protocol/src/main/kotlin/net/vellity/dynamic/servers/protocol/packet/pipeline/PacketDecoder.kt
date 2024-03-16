package net.vellity.dynamic.servers.protocol.packet.pipeline

import io.netty.buffer.ByteBuf
import io.netty.buffer.EmptyByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import net.vellity.dynamic.servers.protocol.packet.PacketBuffer
import net.vellity.dynamic.servers.protocol.packet.registry.PacketRegistry

class PacketDecoder : ByteToMessageDecoder() {
  override fun decode(ctx: ChannelHandlerContext?, `in`: ByteBuf?, out: MutableList<Any>?) {
    // skip if the buffer is empty
    if (`in` is EmptyByteBuf || `in` == null) {
      return
    }
    try {
      val buffer = PacketBuffer(`in`)
      val readVarInt = buffer.readVarInt()
      val createPacket = PacketRegistry.createPacket(readVarInt)
      createPacket.read(buffer)
      out?.add(createPacket)
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }
}