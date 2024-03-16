package net.vellity.dynamic.servers.log.storage.rest

import net.vellity.dynamic.servers.log.storage.entity.StoredLogsRepository
import net.vellity.dynamic.servers.log.storage.rest.dto.DatabaseStatsDto
import net.vellity.dynamic.servers.log.storage.rest.dto.StoreLogRequestDto
import net.vellity.dynamic.servers.log.storage.rest.dto.StoredLogDto
import net.vellity.dynamic.servers.log.storage.storage.LogStorage
import net.vellity.dynamic.servers.log.storage.storage.MongoDBLogStorage
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.time.Clock
import java.time.LocalDateTime
import java.time.ZoneOffset

@RestController
class DirectLogStorageRepository(
  private val logStorage: LogStorage,
  private val logsRepository: StoredLogsRepository
) : LogStorageController {
  override fun storeLog(request: StoreLogRequestDto): ResponseEntity<Unit> {
    logStorage.storeLog(request.serverId, request.content)
    logger.info("Stored log for server ${request.serverId} with size ${request.content.size}")
    return ResponseEntity.ok().build()
  }

  override fun getLogData(serverId: String): ResponseEntity<StoredLogDto> {
    val findById = logsRepository.findById(serverId)

    if (findById.isEmpty) {
      return ResponseEntity.notFound().build()
    }

    return ResponseEntity.ok(StoredLogDto.fromBusinessModel(findById.get()))
  }

  override fun viewLog(serverId: String): ResponseEntity<String> {
    val log = logStorage.getLog(serverId)
    return if (log != null) {
      ResponseEntity.ok(log.toString(Charsets.UTF_8))
    } else {
      ResponseEntity.notFound().build()
    }
  }

  override fun getDatabaseStats(): ResponseEntity<DatabaseStatsDto> {
    val today = logsRepository.countStoredLogsBySavedAtIsAfter(
      LocalDateTime.now(Clock.systemUTC()).toLocalDate().atStartOfDay().toInstant(
        ZoneOffset.UTC
      )
    )
    val lastMonth = logsRepository.countStoredLogsBySavedAtIsAfter(
      LocalDateTime.now(Clock.systemUTC()).minusMonths(1).toInstant(ZoneOffset.UTC)
    )
    val all = logsRepository.countStoredLogsByContentIsNull()
    return ResponseEntity.ok(DatabaseStatsDto(today, lastMonth, all, MongoDBLogStorage.KEEP_LOGS_DURATION.toDays()))
  }

  companion object {
    private val logger = LoggerFactory.getLogger(LogStorageController::class.java)
  }
}