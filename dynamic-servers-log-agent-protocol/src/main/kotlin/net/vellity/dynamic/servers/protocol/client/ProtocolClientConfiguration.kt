package net.vellity.dynamic.servers.protocol.client

interface ProtocolClientConfiguration {
  fun hostname(): String

  fun port(): Int

  fun password(): String

  fun threads(): Int
}