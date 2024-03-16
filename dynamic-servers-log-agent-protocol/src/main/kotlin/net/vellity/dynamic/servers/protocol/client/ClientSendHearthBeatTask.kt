package net.vellity.dynamic.servers.protocol.client

import net.vellity.dynamic.servers.protocol.packets.connection.ClientHearthBeatPacket
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * This class is responsible for sending heartbeat packets from the client to the server.
 * It uses a single-threaded executor to schedule the sending of heartbeat packets at fixed intervals.
 *
 * @property client The client that will send the heartbeat packets.
 */
class ClientSendHearthBeatTask(private val client: NettyProtocolClient) {

  /**
   * This initializer block schedules the sending of heartbeat packets at fixed intervals.
   * The `sendHeartBeat` method is called every second.
   */
  init {
    Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
      ::sendHeartBeat,
      0,
      1,
      TimeUnit.SECONDS
    )
  }

  /**
   * This method sends a heartbeat packet from the client to the server.
   * It first checks if the client is in a state where it can send packets (CONNECTED or AUTHENTICATED).
   * If the client is in an appropriate state, it sends a `ClientHearthBeatPacket`.
   */
  private fun sendHeartBeat() {
    if (client.state() in listOf(ClientState.CONNECTED, ClientState.AUTHENTICATED)) {
      client.sendPacket(ClientHearthBeatPacket())
    }
  }
}