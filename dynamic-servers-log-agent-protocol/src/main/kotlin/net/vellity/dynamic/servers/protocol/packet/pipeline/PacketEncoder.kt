package net.vellity.dynamic.servers.protocol.packet.pipeline

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import net.vellity.dynamic.servers.protocol.packet.Packet
import net.vellity.dynamic.servers.protocol.packet.PacketBuffer
import net.vellity.dynamic.servers.protocol.packet.registry.PacketRegistry

class PacketEncoder : MessageToByteEncoder<Packet>() {
  override fun encode(ctx: ChannelHandlerContext?, msg: Packet, out: ByteBuf) {
    val packetBuffer = PacketBuffer(out)
    packetBuffer.writeVarInt(PacketRegistry.getPacketId(msg.javaClass))
    msg.write(packetBuffer)
  }
}