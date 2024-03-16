package net.vellity.dynamic.servers.log.viewer.stoppedservers

import net.vellity.dynamic.servers.common.http.server.environmentOrDefault
import net.vellity.dynamic.servers.log.storage.http.client.LogStorageClient
import net.vellity.dynamic.servers.log.storage.http.client.dto.DatabaseStatsDto
import net.vellity.dynamic.servers.log.storage.http.client.dto.StoredLogDto
import net.vellity.dynamic.servers.log.storage.http.client.retrofit.RetrofitLogStorageClient
import net.vellity.dynamic.servers.server.registry.http.client.ServerRegistryClient
import net.vellity.dynamic.servers.server.registry.http.client.dto.ServerHistoryDto
import org.springframework.stereotype.Service

@Service
class LogStorageStoppedServersService(private val serverRegistryClient: ServerRegistryClient) : StoppedServersService {
  private val loggingClient: LogStorageClient = RetrofitLogStorageClient(
    hostname = environmentOrDefault("LOG_STORAGE_HOSTNAME", "http://localhost:8085"),
    apiKey = environmentOrDefault("LOG_STORAGE_API_KEY", "default-api-key"),
    sourceName = "log-viewer"
  )

  override fun getLogData(serverId: String): StoredLogDto? =
    loggingClient.getLogData(serverId)

  override fun databaseStats(): DatabaseStatsDto {
    return loggingClient.getDatabaseStats()
  }

  override fun getHistory(serverId: String): ServerHistoryDto {
    return serverRegistryClient.getServerHistory(serverId)
  }
}