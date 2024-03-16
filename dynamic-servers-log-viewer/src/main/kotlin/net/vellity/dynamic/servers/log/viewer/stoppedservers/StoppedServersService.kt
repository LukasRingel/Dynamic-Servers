package net.vellity.dynamic.servers.log.viewer.stoppedservers

import net.vellity.dynamic.servers.log.storage.http.client.dto.DatabaseStatsDto
import net.vellity.dynamic.servers.log.storage.http.client.dto.StoredLogDto
import net.vellity.dynamic.servers.server.registry.http.client.dto.ServerHistoryDto

interface StoppedServersService {
  fun getLogData(serverId: String): StoredLogDto?

  fun databaseStats(): DatabaseStatsDto

  fun getHistory(serverId: String): ServerHistoryDto
}