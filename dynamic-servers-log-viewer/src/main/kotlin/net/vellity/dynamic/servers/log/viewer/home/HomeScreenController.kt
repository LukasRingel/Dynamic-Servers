package net.vellity.dynamic.servers.log.viewer.home

import net.vellity.dynamic.servers.log.viewer.home.dto.DashboardStatsDto
import net.vellity.dynamic.servers.log.viewer.home.dto.RunningServerDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/api/home")
interface HomeScreenController {
  @GetMapping("/running")
  fun getAllRunningServers(): ResponseEntity<List<RunningServerDto>>

  @GetMapping("/stats")
  fun getDashboardStats(): ResponseEntity<DashboardStatsDto>
}