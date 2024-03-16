package net.vellity.dynamic.servers.log.storage.rest.dto

data class DatabaseStatsDto(
  val todayLogs: Long,
  val lastThirtyDaysLogs: Long,
  val totalLogs: Long,
  val deleteLogsAfterDays: Long
)
