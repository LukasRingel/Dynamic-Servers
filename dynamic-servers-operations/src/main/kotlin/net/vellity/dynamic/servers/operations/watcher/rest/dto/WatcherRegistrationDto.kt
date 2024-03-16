package net.vellity.dynamic.servers.operations.watcher.rest.dto

data class WatcherRegistrationDto(
  val executorId: String,
  val hostname: String,
  val port: Int,
  val apiKey: String
)