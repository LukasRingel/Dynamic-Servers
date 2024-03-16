package net.vellity.dynamic.servers.server.starter.startup.source

import net.vellity.dynamic.servers.common.http.server.environmentOrDefault
import net.vellity.dynamic.servers.server.starter.startup.StartupTask
import net.vellity.dynamic.servers.startup.queue.http.client.StartupQueueClient
import net.vellity.dynamic.servers.startup.queue.http.client.retrofit.RetrofitStartupQueueClient
import org.springframework.stereotype.Component

@Component
class StartupQueueServiceStartupTaskSource : StartupTaskSource {
  private val startupQueueClient: StartupQueueClient = RetrofitStartupQueueClient(
    sourceName = environmentOrDefault("SERVER_STARTER_NAME", "local"),
    apiKey = environmentOrDefault("STARTUP_QUEUE_API_KEY", "default-api-key"),
    baseUrl = environmentOrDefault("STARTUP_QUEUE_HOSTNAME", "http://localhost:8082")
  )

  override fun getNextStartupTask(): StartupTask? {
    return try {
      startupQueueClient.pollNextServerToStart()?.let {
        StartupTask(
          id = it.id,
          templateId = it.templateId,
          environment = it.environment
        )
      }
    } catch (e: Exception) {
      null
    }
  }
}