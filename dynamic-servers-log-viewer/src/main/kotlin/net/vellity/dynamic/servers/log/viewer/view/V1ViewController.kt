package net.vellity.dynamic.servers.log.viewer.view

import net.vellity.dynamic.servers.common.http.server.environmentOrDefault
import net.vellity.dynamic.servers.log.viewer.agent.AgentService
import net.vellity.dynamic.servers.log.viewer.runningservers.RunningServersService
import net.vellity.dynamic.servers.log.viewer.stoppedservers.StoppedServersService
import net.vellity.dynamic.servers.log.viewer.view.dto.LiveViewDataDto
import net.vellity.dynamic.servers.log.viewer.view.dto.OpenLogDecideDto
import net.vellity.dynamic.servers.log.viewer.view.dto.StoppedLogView
import net.vellity.dynamic.servers.server.registry.http.client.dto.ServerHistoryDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class V1ViewController(
  private val stoppedServersService: StoppedServersService,
  private val runningServersService: RunningServersService,
  private val agentService: AgentService
) : ViewController {
  override fun getLogOfStoppedServer(serverId: String): ResponseEntity<StoppedLogView> {
    val storedLogDto = stoppedServersService.getLogData(serverId) ?: return ResponseEntity.notFound().build()
    return ResponseEntity.ok(StoppedLogView.fromBusinessModel(storedLogDto))
  }

  override fun getEventsOfStoppedServer(serverId: String): ResponseEntity<ServerHistoryDto> {
    return ResponseEntity.ok(stoppedServersService.getHistory(serverId))
  }

  override fun getLiveViewData(serverId: String): ResponseEntity<LiveViewDataDto> {
    val registeredServerDto = runningServersService.getAllRunningServers()
      .find { serverDto -> serverDto.id.toString() == serverId }
    val executorOfServer = registeredServerDto
      ?.executorId ?: return ResponseEntity.notFound().build()

    if (!agentService.isAgentConnected(executorOfServer)) {
      return ResponseEntity.ok(
        LiveViewDataDto(
          serverId = serverId,
          ready = false,
          executor = executorOfServer,
          startedAt = 0,
          websocketUrl = "",
          session = ""
        )
      )
    }

    return ResponseEntity.ok(
      LiveViewDataDto(
        serverId = serverId,
        ready = true,
        executor = executorOfServer,
        startedAt = registeredServerDto.createdAt,
        websocketUrl = environmentOrDefault(
          "WEB_WEBSOCKET_URL",
          "ws://localhost:8086/"
        ) + "ws/log",
        session = "$serverId@$executorOfServer"
      )
    )
  }

  override fun decideIfLiveOrStopped(serverId: String): ResponseEntity<OpenLogDecideDto> {
    val storedLogDto = stoppedServersService.getLogData(serverId)

    if (storedLogDto != null) {
      return ResponseEntity.ok(OpenLogDecideDto(live = false))
    }

    val runningServer = runningServersService.getAllRunningServers()
      .find { it.id.toString() == serverId }

    if (runningServer != null) {
      return ResponseEntity.ok(OpenLogDecideDto(live = true))
    }

    return ResponseEntity.notFound().build()
  }
}