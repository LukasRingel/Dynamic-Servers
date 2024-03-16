package net.vellity.dynamic.servers.protocol.client

import net.vellity.dynamic.servers.protocol.logger.NetworkingLogger
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class ClientTryToReconnectTask(private val client: NettyProtocolClient, private val networkingLogger: NetworkingLogger) {
  private var sameStateSinceTries = 0

  init {
    Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate({
      if (client.state() != ClientState.DISCONNECTED) {
        return@scheduleAtFixedRate
      }

      if (sameStateSinceTries < 5) {
        sameStateSinceTries++
        return@scheduleAtFixedRate
      }

      sameStateSinceTries = 0
      networkingLogger.info("Trying to reconnect connection of " + client.connectionName)
      client.connect()
    }, 0, 1, TimeUnit.SECONDS)
  }

}