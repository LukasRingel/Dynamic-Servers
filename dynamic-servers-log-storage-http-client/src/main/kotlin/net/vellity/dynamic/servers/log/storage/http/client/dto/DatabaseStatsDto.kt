package net.vellity.dynamic.servers.log.storage.http.client.dto

data class DatabaseStatsDto(
  val todayLogs: Long,
  val lastThirtyDaysLogs: Long,
  val totalLogs: Long,
  val deleteLogsAfterDays: Long
)
