package net.vellity.dynamic.servers.server.registry.history

import net.vellity.dynamic.servers.server.registry.history.update.Update
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "serverSessionHistory")
class ServerSessionHistory {
  @Id
  var id: String? = null

  var template: String? = null

  var executorId: String? = null

  var createdAt: Instant? = null

  var updates: List<Update>? = null
}