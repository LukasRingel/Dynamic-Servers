package net.vellity.dynamic.servers.protocol.client

import io.netty.bootstrap.Bootstrap
import io.netty.channel.Channel
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.handler.codec.LengthFieldBasedFrameDecoder
import io.netty.handler.codec.LengthFieldPrepender
import net.vellity.dynamic.servers.protocol.PlatformDependencies
import net.vellity.dynamic.servers.protocol.logger.FallbackLogger
import net.vellity.dynamic.servers.protocol.logger.NetworkingLogger
import net.vellity.dynamic.servers.protocol.packet.Packet
import net.vellity.dynamic.servers.protocol.packet.listener.PacketListener
import net.vellity.dynamic.servers.protocol.packet.pipeline.PacketDecoder
import net.vellity.dynamic.servers.protocol.packet.pipeline.PacketEncoder
import net.vellity.dynamic.servers.protocol.packet.registry.PacketRegistry
import java.lang.reflect.ParameterizedType

class NettyProtocolClient(
  val connectionName: String,
  val clientName: String,
  val configuration: ProtocolClientConfiguration,
  private val networkingLogger: NetworkingLogger = FallbackLogger()
) : ProtocolClient {
  private var channel: Channel? = null
  private var state: ClientState = ClientState.IDLE

  private val authenticatedHooks = mutableListOf<() -> Unit>()
  private val disconnectedHooks = mutableListOf<() -> Unit>()

  init {
    ClientTryToReconnectTask(this, networkingLogger)
    ClientSendHearthBeatTask(this)
    PacketRegistry.registerAllPackets()
  }

  override fun connect() {
    networkingLogger.info("Connecting to remote at ${configuration.hostname()}:${configuration.port()} as $clientName")
    changeState(ClientState.CONNECTING)
    val eventLoopGroup = PlatformDependencies.chooseEventLoopGroup(configuration.threads())
    val bootstrap = Bootstrap()
      .group(eventLoopGroup)
      .channel(PlatformDependencies.chooseSocketChannel())
      .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
      .handler(object : ChannelInitializer<Channel>() {
        override fun initChannel(ch: Channel?) {
          val pipeline = ch?.pipeline()
          pipeline?.addLast("frameDecoder", LengthFieldBasedFrameDecoder(Int.MAX_VALUE, 0, 4, 0, 4))
          pipeline?.addLast("packetDecoder", PacketDecoder())
          pipeline?.addLast("framePrepender", LengthFieldPrepender(4))
          pipeline?.addLast("packetEncoder", PacketEncoder())
          pipeline?.addLast("connectionHandler", ClientConnectionHandler(this@NettyProtocolClient, networkingLogger))
        }
      })

    val channelFuture = bootstrap.connect(configuration.hostname(), configuration.port())
    eventLoopGroup.schedule({
      if (!channelFuture.isDone || !channelFuture.isSuccess) {
        networkingLogger.info("Connection to remote at ${configuration.hostname()}:${configuration.port()} timed out")
        networkingLogger.info("Reconnecting in 5 seconds...")
        if (channelFuture.channel() != null) {
          channelFuture.channel().close()
        }
        Thread.sleep(5000)
        connect()
      }
    }, 1, java.util.concurrent.TimeUnit.SECONDS)
    channel = channelFuture.sync().channel()
  }

  override fun sendPacket(packet: Packet) {
    if (channel == null) {
      return
    }

    channel!!.writeAndFlush(packet)
  }

  fun changeState(state: ClientState) {
    this.state = state

    when (state) {
      ClientState.CONNECTED -> {
      }

      ClientState.DISCONNECTED -> {
        networkingLogger.info("Connection $connectionName has been closed")
        disconnectedHooks.forEach { it() }
      }

      ClientState.AUTHENTICATING -> {
      }

      ClientState.AUTHENTICATED -> {
        authenticatedHooks.forEach { it() }
      }

      else -> {
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

  override fun state(): ClientState {
    return state
  }

  fun addAuthenticatedHook(hook: () -> Unit) {
    authenticatedHooks.add(hook)
  }

  fun addDisconnectedHook(hook: () -> Unit) {
    disconnectedHooks.add(hook)
  }
}