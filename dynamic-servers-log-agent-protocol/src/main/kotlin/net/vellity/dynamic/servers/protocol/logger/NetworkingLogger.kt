package net.vellity.dynamic.servers.protocol.logger

interface NetworkingLogger {
  fun info(message: String)
  fun error(message: String)
  fun error(message: String, throwable: Throwable)
}