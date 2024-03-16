package net.vellity.dynamic.servers.protocol.server

import io.netty.bootstrap.ServerBootstrap
import io.netty.buffer.PooledByteBufAllocator
import io.netty.channel.Channel
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.EventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.LengthFieldBasedFrameDecoder
import io.netty.handler.codec.LengthFieldPrepender
import net.vellity.dynamic.servers.protocol.ConnectedClient
import net.vellity.dynamic.servers.protocol.PlatformDependencies
import net.vellity.dynamic.servers.protocol.client.ClientState
import net.vellity.dynamic.servers.protocol.logger.NetworkingLogger
import net.vellity.dynamic.servers.protocol.packet.Packet
import net.vellity.dynamic.servers.protocol.packet.listener.PacketListener
import net.vellity.dynamic.servers.protocol.packet.pipeline.PacketDecoder
import net.vellity.dynamic.servers.protocol.packet.pipeline.PacketEncoder
import net.vellity.dynamic.servers.protocol.packet.registry.PacketRegistry
import net.vellity.dynamic.servers.protocol.packets.connection.RemoteShuttingDownPacket
import java.lang.reflect.ParameterizedType
import java.util.function.Predicate

class NettyProtocolServer(
  val configuration: ProtocolServerConfiguration,
  private val networkingLogger: NetworkingLogger,
  private val instanceName: String
) : ProtocolServer {
  private var bossGroup: EventLoopGroup? = null
  private var workerGroup: EventLoopGroup? = null

  private var connectedClients = mutableListOf<ConnectedClient>()

  private val clientConnectedHook = mutableListOf<(ConnectedClient) -> Unit>()
  private val clientDisconnectedHook = mutableListOf<(ConnectedClient) -> Unit>()

  init {
    PacketRegistry.registerAllPackets()
  }

  override fun start() {
    bossGroup = PlatformDependencies.chooseEventLoopGroup(0)
    workerGroup = PlatformDependencies.chooseEventLoopGroup(configuration.workerThreads())

    val bootstrap = ServerBootstrap()
    bootstrap.group(bossGroup, workerGroup)
      // we use the NioServerSocketChannel
      .channel(PlatformDependencies.chooseServerSocketChannel())
      // no delay between the requests
      .childOption(ChannelOption.TCP_NODELAY, true)
      // reset connections without closing them properly
      .childOption(ChannelOption.SO_LINGER, 0)
      // keep the connection alive
      .childOption(ChannelOption.SO_KEEPALIVE, true)
      // we want to use a pooled byte buffer allocator
      .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator(true))
      // set the high and high watermark to 32kb
      .childOption(ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK, 32 * 1024)
      // set the low and low watermark to 8kb
      .childOption(ChannelOption.WRITE_BUFFER_LOW_WATER_MARK, 8 * 1024)
      // we use our default child handler
      .childHandler(object : ChannelInitializer<SocketChannel>() {
        override fun initChannel(ch: SocketChannel?) {
          val pipeline = ch?.pipeline()
          pipeline?.addLast("frameDecoder", LengthFieldBasedFrameDecoder(Int.MAX_VALUE, 0, 4, 0, 4))
          pipeline?.addLast("messageDecoder", PacketDecoder())
          pipeline?.addLast("framePrepender", LengthFieldPrepender(4))
          pipeline?.addLast("messageEncoder", PacketEncoder())
          pipeline?.addLast(
            "connectionHandler",
            NettyServerConnectionHandler(this@NettyProtocolServer, networkingLogger)
          )
        }
      })

    // bind the server to the port and wait for connections
    val channelFuture = bootstrap.bind(configuration.port()).sync()
    Runtime.getRuntime().addShutdownHook(Thread { stop() })
    networkingLogger.info(instanceName + " started on port ${configuration.port()} with ${configuration.workerThreads()} worker threads")
    channelFuture.channel().closeFuture().sync()
  }

  override fun stop() {
    networkingLogger.info("Shutting down $instanceName")
    for (connectedClient in connectedClients) {
      connectedClient.sendPacket(RemoteShuttingDownPacket())
    }
    Thread.sleep(500)
    workerGroup?.shutdownGracefully()
    bossGroup?.shutdownGracefully()
    workerGroup = null
    bossGroup = null
  }

  override fun isRunning(): Boolean {
    return bossGroup != null && workerGroup != null
  }

  override fun broadcastPacket(packet: Packet) {
    for (connectedClient in connectedClients) {
      connectedClient.sendPacket(packet)
    }
  }

  override fun broadcastPacket(packet: Packet, filter: Predicate<ConnectedClient>) {
    for (connectedClient in connectedClients) {
      if (filter.test(connectedClient)) {
        connectedClient.sendPacket(packet)
      }
    }
  }

  override fun registerListener(listener: PacketListener<*>) {
    try {
      for (type in listener.javaClass.genericInterfaces) {
        if (ParameterizedType::class.java.isAssignableFrom(type.javaClass)) {
          val parameterizedType = (type as ParameterizedType).actualTypeArguments[0]
          if (Packet::class.java.isAssignableFrom(parameterizedType as Class<*>)) {
            networkingLogger.info("Registered listener for packet ${parameterizedType.simpleName}")
            PacketRegistry.registerListener(
              parameterizedType as Class<Packet>,
              listener as PacketListener<Packet>
            )
          } else {
            networkingLogger.info("Could not register listener for packet ${parameterizedType.simpleName}")
            networkingLogger.info("Packet ${parameterizedType.simpleName} is not a packet")
          }
        } else {
          networkingLogger.info("Could not register listener for packet ${type.typeName}")
          networkingLogger.info("Packet ${type.typeName} is not a class")
        }
      }
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  fun findClientByChannel(channel: Channel): ConnectedClient? {
    return connectedClients.find { it.channel == channel }
  }

  fun removeClient(client: ConnectedClient) {
    connectedClients.remove(client)
    clientDisconnectedHook.forEach { it(client) }
  }

  fun addClient(client: ConnectedClient) {
    connectedClients.add(client)
  }

  fun changeClientState(client: ConnectedClient, state: ClientState) {
    client.state = state
    if (state == ClientState.CONNECTED) {
      clientConnectedHook.forEach { it(client) }
    }
  }

  fun findClientsWithState(state: ClientState): List<ConnectedClient> {
    return connectedClients.filter { it.state == state }
  }

  override fun addClientConnectedHook(hook: (ConnectedClient) -> Unit) {
    clientConnectedHook.add(hook)
  }

  override fun addClientDisconnectedHook(hook: (ConnectedClient) -> Unit) {
    clientDisconnectedHook.add(hook)
  }
}