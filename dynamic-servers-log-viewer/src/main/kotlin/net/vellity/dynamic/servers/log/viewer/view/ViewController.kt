package net.vellity.dynamic.servers.log.viewer.view

import net.vellity.dynamic.servers.log.viewer.view.dto.LiveViewDataDto
import net.vellity.dynamic.servers.log.viewer.view.dto.OpenLogDecideDto
import net.vellity.dynamic.servers.log.viewer.view.dto.StoppedLogView
import net.vellity.dynamic.servers.server.registry.http.client.dto.ServerHistoryDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@RequestMapping("/api/view")
interface ViewController {
  @GetMapping("/stopped")
  fun getLogOfStoppedServer(@RequestParam serverId: String): ResponseEntity<StoppedLogView>

  @GetMapping("/stopped/events")
  fun getEventsOfStoppedServer(@RequestParam serverId: String): ResponseEntity<ServerHistoryDto>

  @GetMapping("/live")
  fun getLiveViewData(@RequestParam serverId: String): ResponseEntity<LiveViewDataDto>

  @GetMapping("/decide")
  fun decideIfLiveOrStopped(@RequestParam serverId: String): ResponseEntity<OpenLogDecideDto>
}