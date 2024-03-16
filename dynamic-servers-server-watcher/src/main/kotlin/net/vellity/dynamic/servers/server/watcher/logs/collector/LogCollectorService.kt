package net.vellity.dynamic.servers.server.watcher.logs.collector

interface LogCollectorService {
  fun collectLogForServer(serverId: String): ByteArray

  fun streamLogsForServer(serverId: String, consumer: (ByteArray) -> Unit)
}