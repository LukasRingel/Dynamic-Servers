package net.vellity.dynamic.servers.server.watcher.logs.collector

import net.vellity.dynamic.servers.server.watcher.container.ContainerEngine
import org.springframework.stereotype.Service

@Service
class EngineLogCollectorService(private val engine: ContainerEngine) : LogCollectorService {
  override fun collectLogForServer(serverId: String): ByteArray {
    return engine.logsForContainer(serverId)
  }

  override fun streamLogsForServer(serverId: String, consumer: (ByteArray) -> Unit) {
    try {
      engine.streamLogsForContainer(serverId, consumer)
    } catch (e: InterruptedException) {
      // ignore since the driver always interrupts the thread if the container is stopped
    }
  }
}