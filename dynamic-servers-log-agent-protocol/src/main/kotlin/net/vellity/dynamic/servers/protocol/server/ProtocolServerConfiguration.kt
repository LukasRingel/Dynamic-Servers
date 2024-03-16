package net.vellity.dynamic.servers.protocol.server

interface ProtocolServerConfiguration {
  fun port(): Int

  fun password(): String

  fun workerThreads(): Int
}