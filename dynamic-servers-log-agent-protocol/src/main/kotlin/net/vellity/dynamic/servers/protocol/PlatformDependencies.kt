package net.vellity.dynamic.servers.protocol

import io.netty.channel.Channel
import io.netty.channel.EventLoopGroup
import io.netty.channel.ServerChannel
import io.netty.channel.epoll.Epoll
import io.netty.channel.epoll.EpollEventLoopGroup
import io.netty.channel.epoll.EpollServerSocketChannel
import io.netty.channel.epoll.EpollSocketChannel
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.channel.socket.nio.NioSocketChannel

/**
 * This object contains methods that are used to choose the correct event loop group and socket channel
 * based on the operating system the server is running on
 *
 * This is done because the epoll event loop group and socket channel are only available on linux
 * The nio event loop group and socket channel are available on all operating systems
 */

object PlatformDependencies {
  /**
   * This method chooses the event loop group based on the operating system
   * If the operating system is linux we use the epoll event loop group
   * If the operating system is windows we use the nio event loop group
   */
  fun chooseEventLoopGroup(threads: Int): EventLoopGroup {
    return when {
      Epoll.isAvailable() -> EpollEventLoopGroup(threads)
      else -> NioEventLoopGroup(threads)
    }
  }

  /**
   * This method chooses the socket channel based on the operating system
   * If the operating system is linux we use the epoll socket channel
   * If the operating system is windows we use the nio socket channel
   */
  fun chooseSocketChannel(): Class<out Channel> {
    return when {
      Epoll.isAvailable() -> EpollSocketChannel::class.java
      else -> NioSocketChannel::class.java
    }
  }

  /**
   * This method chooses the socket channel based on the operating system
   * If the operating system is linux we use the epoll socket channel
   * If the operating system is windows we use the nio socket channel
   */
  fun chooseServerSocketChannel(): Class<out ServerChannel> {
    return when {
      Epoll.isAvailable() -> EpollServerSocketChannel::class.java
      else -> NioServerSocketChannel::class.java
    }
  }
}