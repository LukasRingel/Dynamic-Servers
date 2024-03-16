package net.vellity.dynamic.servers.server.registry.history

import org.springframework.data.mongodb.repository.MongoRepository
import java.time.Instant

interface SessionHistoryRepository : MongoRepository<ServerSessionHistory, String> {
  fun findServerSessionHistoriesByCreatedAtBetween(minDate: Instant, maxDate: Instant): List<ServerSessionHistory>
}