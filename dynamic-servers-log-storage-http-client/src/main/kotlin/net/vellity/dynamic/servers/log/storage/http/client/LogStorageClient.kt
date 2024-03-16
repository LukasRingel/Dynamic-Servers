package net.vellity.dynamic.servers.log.storage.http.client

import net.vellity.dynamic.servers.log.storage.http.client.dto.DatabaseStatsDto
import net.vellity.dynamic.servers.log.storage.http.client.dto.StoredLogDto

interface LogStorageClient {
  fun storeLog(name: String, content: ByteArray)

  fun getLogData(serverId: String): StoredLogDto?

  fun getDatabaseStats(): DatabaseStatsDto
}