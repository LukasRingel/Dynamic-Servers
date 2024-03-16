package net.vellity.dynamic.servers.server.registry.history.update

import net.vellity.dynamic.servers.common.http.server.utcNow
import java.time.Instant

class Update {
  var type: UpdateType? = null

  var timestamp: Instant? = utcNow()

  var metaData: Map<String, String>? = null
}