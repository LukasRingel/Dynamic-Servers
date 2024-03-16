package net.vellity.dynamic.servers.server.registry.history.rest

import net.vellity.dynamic.servers.common.http.server.utcNow
import net.vellity.dynamic.servers.server.registry.history.SessionHistoryRepository
import net.vellity.dynamic.servers.server.registry.history.update.UpdateType
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
class DirectServerHistoryController(private val sessionHistoryRepository: SessionHistoryRepository) :
  SessionHistoryController {
  override fun getHistory(serverId: String): ResponseEntity<ServerHistoryDto> {
    val maybeHistory = sessionHistoryRepository.findById(serverId)
    if (maybeHistory.isEmpty) {
      return ResponseEntity.notFound().build()
    }
    val history = maybeHistory.get()
    return ResponseEntity.ok(ServerHistoryDto(
      serverId,
      history.template!!,
      history.executorId!!,
      history.createdAt!!.toEpochMilli(),
      history.updates!!.map {
        UpdateDto(
          it.type!!,
          it.timestamp!!.toEpochMilli(),
          it.metaData ?: emptyMap()
        )
      }
    ))
  }

  override fun findHistories(
    limit: Int,
    minDate: Long,
    maxDate: Long,
    template: String,
    executor: String
  ): ResponseEntity<List<ServerHistoryDto>> {
    val startDate = Instant.ofEpochMilli(minDate)
    val endDate = Instant.ofEpochMilli(maxDate)

    val histories = sessionHistoryRepository.findServerSessionHistoriesByCreatedAtBetween(startDate, endDate)
      .toMutableList()

    if (template.isNotEmpty()) {
      histories.removeIf { it.template != template }
    }

    if (executor.isNotEmpty()) {
      histories.removeIf { it.executorId != executor }
    }

    if (histories.size > limit) {
      histories.sortByDescending { history ->
        history.updates!!.find { it.type == UpdateType.STATUS_UPDATE_STOPPING_TO_STOPPED }?.timestamp ?: utcNow()
      }
      histories.subList(limit, histories.size).clear()
    }

    return ResponseEntity.ok(histories.map { history ->
      ServerHistoryDto(
        history.id!!,
        history.template!!,
        history.executorId!!,
        history.createdAt!!.toEpochMilli(),
        history.updates!!.map {
          UpdateDto(
            it.type!!,
            it.timestamp!!.toEpochMilli(),
            it.metaData ?: emptyMap()
          )
        }
      )
    })
  }
}