package net.vellity.dynamic.servers.log.storage.purge

import net.vellity.dynamic.servers.common.http.server.utcNow
import net.vellity.dynamic.servers.log.storage.entity.StoredLogsRepository
import org.slf4j.LoggerFactory
import org.springframework.scheduling.concurrent.CustomizableThreadFactory
import org.springframework.stereotype.Component
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@Component
class PurgeOutdatedLogs(private val storedLogsRepository: StoredLogsRepository) {
  init {
    Executors.newSingleThreadScheduledExecutor(CustomizableThreadFactory("purge-"))
      .scheduleAtFixedRate(
        { tick() },
        0,
        1,
        TimeUnit.HOURS
      )
  }

  private fun tick() {
    logger.info("Purging outdated logs...")
    val storedLogs = storedLogsRepository.findAllByDoNotDeleteBeforeIsBefore(utcNow())
    storedLogs.filter { log -> log.deletedAt == null }.forEach { storedLog ->
      storedLog.content = null
      storedLog.deletedAt = utcNow()
      storedLogsRepository.save(storedLog).run {
        logger.info("Deleted stored log ${storedLog.id} because it was outdated")
      }
    }
  }

  companion object {
    private val logger = LoggerFactory.getLogger(PurgeOutdatedLogs::class.java)
  }
}