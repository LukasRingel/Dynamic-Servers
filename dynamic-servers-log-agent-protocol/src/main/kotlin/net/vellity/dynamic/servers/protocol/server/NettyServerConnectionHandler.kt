package net.vellity.dynamic.servers.protocol.server

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import net.vellity.dynamic.servers.protocol.ConnectedClient
import net.vellity.dynamic.servers.protocol.client.ClientState
import net.vellity.dynamic.servers.protocol.logger.NetworkingLogger
import net.vellity.dynamic.servers.protocol.packet.Packet
import net.vellity.dynamic.servers.protocol.packet.registry.PacketRegistry
import net.vellity.dynamic.servers.protocol.packets.authentication.ClientTryAuthenticatePacket
import net.vellity.dynamic.servers.protocol.packets.authentication.YouAreNowAuthenticatedPacket
import net.vellity.dynamic.servers.protocol.packets.authentication.YouSentInvalidPasswordPacket
import net.vellity.dynamic.servers.protocol.packets.authentication.YouShouldAuthenticatePacket
import net.vellity.dynamic.servers.protocol.packets.connection.ClientHearthBeatPacket

class NettyServerConnectionHandler(
  private val nettyServer: NettyProtocolServer,
  private val logger: NetworkingLogger
) : SimpleChannelInboundHandler<Packet>() {
  override fun channelInactive(ctx: ChannelHandlerContext) {
    findClientByChannel(ctx)?.let {
      nettyServer.removeClient(it)
    }
  }

  override fun channelActive(ctx: ChannelHandlerContext) {
    val client = ConnectedClient("???", ctx.channel())
    nettyServer.addClient(client)
    client.sendPacket(YouShouldAuthenticatePacket())
  }

  override fun channelRead0(ctx: ChannelHandlerContext?, msg: Packet?) {

    if (msg == null) {
      logger.error("Received null packet from ${ctx?.channel()?.remoteAddress()}")
      return
    }

    val client = findClientByChannel(ctx!!) ?: return
    if (client.state != ClientState.AUTHENTICATED && client.state != ClientState.CONNECTED) {

      if (msg is ClientTryAuthenticatePacket) {
        if (msg.password != nettyServer.configuration.password()) {
          logger.error("Client ${formattedRemoteAddress(client)} failed to authenticate with password ${msg.password}")
          client.sendPacket(YouSentInvalidPasswordPacket())
          nettyServer.changeClientState(client, ClientState.AUTHENTICATION_FAILED)
          client.closeConnection()
          return
        }

        client.name = msg.name
        nettyServer.changeClientState(client, ClientState.CONNECTED)
        client.sendPacket(YouAreNowAuthenticatedPacket())
        return
      }

      client.closeConnection()
      return
    }

    if (msg is ClientHearthBeatPacket) {
      client.lastHeartbeat = System.currentTimeMillis()
      return
    }

    PacketRegistry.callListeners(
      msg,
      client
    )
  }

  override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
  }

  private fun findClientByChannel(channel: ChannelHandlerContext): ConnectedClient? {
    return nettyServer.findClientByChannel(channel.channel())
  }

  companion object {
    fun formattedRemoteAddress(client: ConnectedClient): String {
      return client.channel.remoteAddress().toString().substring(1)
    }
  }
}