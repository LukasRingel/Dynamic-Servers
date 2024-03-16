package net.vellity.dynamic.servers.server.watcher.operations

import net.vellity.dynamic.servers.server.watcher.operations.client.OperationsRestClient
import net.vellity.dynamic.servers.server.watcher.operations.client.RetrofitOperationsRestClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

@Component
class ServiceOperations : Operations {
  private val restClient: OperationsRestClient = RetrofitOperationsRestClient()

  private val registered = AtomicBoolean(false)

  init {
    Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate({
      tick()
    }, 0, 1, TimeUnit.SECONDS)
  }

  override fun registerToOperations() {
    restClient.registerToOperations()
    registered.set(true)
  }

  override fun heartbeat() {
    restClient.heartbeat()
  }

  private fun tick() {
    try {
      if (!registered.get()) {
        registerToOperations()
      }
      heartbeat()
    } catch (e: Exception) {
      registered.set(false)
      logger.info("Failed to send heartbeat to operations")
    }
  }
  companion object {
    private val logger = LoggerFactory.getLogger(Operations::class.java)
  }
}