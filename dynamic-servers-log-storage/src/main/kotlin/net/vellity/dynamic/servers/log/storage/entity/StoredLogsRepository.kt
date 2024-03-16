package net.vellity.dynamic.servers.log.storage.entity

import org.springframework.data.mongodb.repository.MongoRepository
import java.time.Instant

interface StoredLogsRepository : MongoRepository<StoredLog, String> {
  fun findAllByDoNotDeleteBeforeIsBefore(doNotDeleteBefore: Instant): List<StoredLog>

  fun countStoredLogsBySavedAtIsAfter(savedAt: Instant): Long

  fun countStoredLogsByContentIsNull(): Long
}
