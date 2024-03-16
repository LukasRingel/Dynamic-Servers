package net.vellity.dynamic.servers.operations.connection.startup

import net.vellity.dynamic.servers.common.http.server.environmentOrDefault
import net.vellity.dynamic.servers.startup.queue.http.client.StartupQueueClient
import net.vellity.dynamic.servers.startup.queue.http.client.retrofit.RetrofitStartupQueueClient
import org.springframework.stereotype.Component

@Component
class ServiceStartupQueue : StartupQueue {

  private val startupQueueClient: StartupQueueClient = RetrofitStartupQueueClient(
    sourceName = "operations",
    baseUrl = environmentOrDefault("STARTUP_QUEUE_HOSTNAME", "http://localhost:8082"),
    apiKey = environmentOrDefault("STARTUP_QUEUE_API_KEY", "default-api-key")
  )

  override fun insertTemplateToQueue(template: String, priority: Int, environment: Map<String, String>) {
    startupQueueClient.addServerToStart(template, priority, environment)
  }
}