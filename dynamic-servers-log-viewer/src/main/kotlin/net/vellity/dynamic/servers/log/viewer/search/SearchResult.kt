package net.vellity.dynamic.servers.log.viewer.search

data class SearchResult(
  val serverId: String,
  val executorId: String,
  val template: String,
  val createdAt: Long,
  val stoppedAt: Long,
)