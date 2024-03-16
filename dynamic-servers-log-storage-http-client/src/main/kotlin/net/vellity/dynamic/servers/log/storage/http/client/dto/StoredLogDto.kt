package net.vellity.dynamic.servers.log.storage.http.client.dto

data class StoredLogDto(
  val deletedAt: Long?,
  val doNotDeleteBefore: Long?,
  val content: String
)