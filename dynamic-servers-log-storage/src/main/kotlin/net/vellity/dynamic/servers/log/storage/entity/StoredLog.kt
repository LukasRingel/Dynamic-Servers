package net.vellity.dynamic.servers.log.storage.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "storedLogs")
class StoredLog {
  @Id
  var id: String? = null

  var content: ByteArray? = null

  var doNotDeleteBefore: Instant? = null

  var deletedAt: Instant? = null

  @CreatedDate
  var savedAt: Instant = Instant.now()
}