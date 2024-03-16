package net.vellity.dynamic.servers.log.viewer.home

import net.vellity.dynamic.servers.log.viewer.home.dto.DashboardStatsDto
import net.vellity.dynamic.servers.log.viewer.home.dto.RunningServerDto
import net.vellity.dynamic.servers.log.viewer.runningservers.RunningServersService
import net.vellity.dynamic.servers.log.viewer.stoppedservers.StoppedServersService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class V1HomeScreenController(
  private val runningServersService: RunningServersService,
  private val stoppedServersService: StoppedServersService
) : HomeScreenController {
  override fun getAllRunningServers(): ResponseEntity<List<RunningServerDto>> {
    return ResponseEntity.ok(
      runningServersService.getAllRunningServers()
        .map { RunningServerDto.fromBusinessModel(it) }
    )
  }

  override fun getDashboardStats(): ResponseEntity<DashboardStatsDto> {
    val databaseStats = stoppedServersService.databaseStats()
    return ResponseEntity.ok(
      DashboardStatsDto(
        todayLogs = databaseStats.todayLogs,
        lastThirtyDaysLogs = databaseStats.lastThirtyDaysLogs,
        totalLogs = databaseStats.totalLogs,
        deleteLogsAfterDays = databaseStats.deleteLogsAfterDays
      )
    )
  }
}