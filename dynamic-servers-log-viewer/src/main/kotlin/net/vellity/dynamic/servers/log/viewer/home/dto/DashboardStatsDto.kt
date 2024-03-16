package net.vellity.dynamic.servers.log.viewer.home.dto

data class DashboardStatsDto(
  val todayLogs: Long,
  val lastThirtyDaysLogs: Long,
  val totalLogs: Long,
  val deleteLogsAfterDays: Long
)
