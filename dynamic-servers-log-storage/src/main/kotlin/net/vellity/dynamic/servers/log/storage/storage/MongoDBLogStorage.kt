package net.vellity.dynamic.servers.log.storage.storage

import net.vellity.dynamic.servers.common.http.server.environmentOrDefault
import net.vellity.dynamic.servers.common.http.server.utcNow
import net.vellity.dynamic.servers.log.storage.entity.StoredLog
import net.vellity.dynamic.servers.log.storage.entity.StoredLogsRepository
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class MongoDBLogStorage(private val repository: StoredLogsRepository) : LogStorage {
  override fun storeLog(name: String, content: ByteArray) {
    val log = StoredLog()
    log.id = name
    log.content = content
    log.doNotDeleteBefore = utcNow().plus(KEEP_LOGS_DURATION)
    repository.save(log)
  }

  override fun getLog(name: String): ByteArray? {
    val log = repository.findById(name)
    if (log.isPresent) {
      return log.get().content
    }
    return null
  }

  companion object {
    val KEEP_LOGS_DURATION = Duration.ofDays(
      environmentOrDefault(
        "LOG_STORAGE_KEEP_LOGS_DURATION",
        "90"
      ).toLong()
    )
  }
}