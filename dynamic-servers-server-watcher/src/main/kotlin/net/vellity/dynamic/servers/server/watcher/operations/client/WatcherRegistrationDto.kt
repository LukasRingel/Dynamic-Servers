package net.vellity.dynamic.servers.server.watcher.operations.client

data class WatcherRegistrationDto(
  val executorId: String,
  val hostname: String,
  val port: Int,
  val apiKey: String
)