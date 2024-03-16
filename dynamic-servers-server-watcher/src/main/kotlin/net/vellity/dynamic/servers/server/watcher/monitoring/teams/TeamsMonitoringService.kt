package net.vellity.dynamic.servers.server.watcher.monitoring.teams

import net.vellity.dynamic.servers.common.http.server.environmentOrDefault
import net.vellity.dynamic.servers.server.watcher.monitoring.Incident
import net.vellity.dynamic.servers.server.watcher.monitoring.MonitoringService
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.util.concurrent.Executors

@Service
class TeamsMonitoringService : MonitoringService {
  override fun announceIncident(incident: Incident, containerId: String) {
    if (teamWebhookUrl == "silent") {
      return
    }

    executorService.submit {
      sendWebhookToTeams(incident, containerId)
    }
  }

  private fun sendWebhookToTeams(
    incident: Incident,
    containerId: String
  ) {
    val content: MutableMap<String, Any> = mutableMapOf()
    content["@type"] = "MessageCard"
    content["@context"] = "https://schema.org/extensions"
    content["@themeColor"] = "ff4f4f"
    content["summary"] = incident.name
    content["sections"] = listOf(
      Section(
        facts = if (containerId.isEmpty()) {
          incidentOnWatcher(incident)
        } else {
          incidentInContainer(incident, containerId)
        }
      )
    )

    RestTemplate().postForEntity(
      teamWebhookUrl,
      HttpEntity(content, HttpHeaders().apply {
        contentType = MediaType.APPLICATION_JSON
      }),
      String::class.java
    )
  }

  private fun incidentInContainer(incident: Incident, containerId: String): List<Content> = listOf(
    Content("Type", incident.name),
    Content("Container", containerId),
    Content("Executor", executorId)
  )

  private fun incidentOnWatcher(incident: Incident): List<Content> = listOf(
    Content("Type", incident.name),
    Content("Executor", executorId)
  )

  companion object {
    private val teamWebhookUrl = environmentOrDefault(
      "TEAMS_MONITORING_URL",
      "silent"
    )
    private val executorId = environmentOrDefault(
      "SERVER_STARTER_NAME",
      "local"
    )
    private val executorService = Executors.newSingleThreadExecutor()
  }
}