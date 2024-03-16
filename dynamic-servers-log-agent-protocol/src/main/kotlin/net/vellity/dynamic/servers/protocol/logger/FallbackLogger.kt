package net.vellity.dynamic.servers.protocol.logger

class FallbackLogger: NetworkingLogger {
  override fun info(message: String) {
    println("[INFO] $message")
  }

  override fun error(message: String) {
    println("[ERROR] $message")
  }

  override fun error(message: String, throwable: Throwable) {
    println("[ERROR] $message")
    throwable.printStackTrace()
  }
}