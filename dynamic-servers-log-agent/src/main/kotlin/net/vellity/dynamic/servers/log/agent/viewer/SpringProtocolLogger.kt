package net.vellity.dynamic.servers.log.agent.viewer

import net.vellity.dynamic.servers.protocol.logger.NetworkingLogger
import org.slf4j.LoggerFactory

class SpringProtocolLogger : NetworkingLogger {
  private val logger = LoggerFactory.getLogger("Log-Viewer")

  override fun info(message: String) {
    logger.info(message)
  }

  override fun error(message: String) {
    logger.error(message)
  }

  override fun error(message: String, throwable: Throwable) {
    logger.error(message, throwable)
  }
}