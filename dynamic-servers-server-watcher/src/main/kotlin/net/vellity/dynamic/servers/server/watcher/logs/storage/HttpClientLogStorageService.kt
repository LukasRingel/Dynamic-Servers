package net.vellity.dynamic.servers.server.watcher.logs.storage

import net.vellity.dynamic.servers.common.http.server.environmentOrDefault
import net.vellity.dynamic.servers.log.storage.http.client.LogStorageClient
import net.vellity.dynamic.servers.log.storage.http.client.retrofit.RetrofitLogStorageClient
import org.springframework.stereotype.Service

@Service
class HttpClientLogStorageService : LogStorageService {
  private val loggingClient: LogStorageClient = RetrofitLogStorageClient(
    hostname = environmentOrDefault("LOG_STORAGE_HOSTNAME", "http://localhost:8085"),
    apiKey = environmentOrDefault("LOG_STORAGE_API_KEY", "default-api-key"),
    sourceName = environmentOrDefault("LOG_STORAGE_NAME", "local")
  )

  override fun storeLog(name: String, content: ByteArray) {
    loggingClient.storeLog(name, content)
  }
}