package net.vellity.dynamic.servers.log.viewer.search

import net.vellity.dynamic.servers.server.registry.http.client.ServerRegistryClient
import net.vellity.dynamic.servers.server.registry.http.client.dto.UpdateType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class DirectSearchScreenController(private val serverRegistryClient: ServerRegistryClient) : SearchScreenController {
  override fun getWithFilter(
    template: String,
    executor: String,
    limit: Int,
    before: Long,
    after: Long
  ): ResponseEntity<List<SearchResult>> {

    val findHistories = serverRegistryClient.findHistories(
      limit = limit,
      template = template,
      executor = executor,
      minDate = after,
      maxDate = before
    )

    return ResponseEntity.ok(
      findHistories.sortedByDescending { historyDto ->
        historyDto.updates.find { it.type == UpdateType.STATUS_UPDATE_STOPPING_TO_STOPPED }?.timestamp ?: 0
      }.map { historyDto ->
        SearchResult(
          serverId = historyDto.serverId,
          executorId = historyDto.executorId,
          template = historyDto.template,
          createdAt = historyDto.createdAt,
          stoppedAt = historyDto.updates.find { it.type == UpdateType.STATUS_UPDATE_STOPPING_TO_STOPPED }?.timestamp
            ?: 0
        )
      })
  }
}