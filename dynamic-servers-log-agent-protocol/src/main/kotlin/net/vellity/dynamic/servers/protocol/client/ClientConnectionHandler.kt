package net.vellity.dynamic.servers.protocol.client

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import net.vellity.dynamic.servers.protocol.ConnectedClient
import net.vellity.dynamic.servers.protocol.logger.NetworkingLogger
import net.vellity.dynamic.servers.protocol.packet.Packet
import net.vellity.dynamic.servers.protocol.packet.registry.PacketRegistry
import net.vellity.dynamic.servers.protocol.packets.authentication.*
import net.vellity.dynamic.servers.protocol.packets.connection.RemoteShuttingDownPacket

class ClientConnectionHandler(
  private val client: NettyProtocolClient,
  private val networkingLogger: NetworkingLogger
) :
  SimpleChannelInboundHandler<Packet>() {
  private var connectedClient: ConnectedClient? = null

  override fun channelActive(ctx: ChannelHandlerContext) {
    client.changeState(ClientState.CONNECTED)
    connectedClient = ConnectedClient(client.connectionName, ctx.channel())
  }

  override fun channelInactive(ctx: ChannelHandlerContext) {
    client.changeState(ClientState.DISCONNECTED)
    connectedClient = null
  }

  override fun channelRead0(ctx: ChannelHandlerContext?, msg: Packet?) {
    if (msg == null) {
      networkingLogger.error("Received null packet from ${ctx?.channel()?.remoteAddress()}")
      return
    }

    if (msg is YouShouldAuthenticatePacket) {
      client.changeState(ClientState.AUTHENTICATING)
      connectedClient!!.sendPacket(
        ClientTryAuthenticatePacket(
          client.clientName,
          client.configuration.password()
        )
      )
      return
    }

    if (msg is YouAreNowAuthenticatedPacket) {
      client.changeState(ClientState.AUTHENTICATED)
      return
    }

    if (msg is YouTookToLongToAuthenticatePacket) {
      client.changeState(ClientState.AUTHENTICATION_FAILED)
      return
    }

    if (msg is YouSentInvalidPasswordPacket) {
      client.changeState(ClientState.AUTHENTICATION_FAILED)
      return
    }

    if (msg is RemoteShuttingDownPacket) {
      networkingLogger.info("Remote of " + client.connectionName + " is shutting down...")
      return
    }

    PacketRegistry.callListeners(
      msg,
      connectedClient!!
    )
  }


  override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
  }
}