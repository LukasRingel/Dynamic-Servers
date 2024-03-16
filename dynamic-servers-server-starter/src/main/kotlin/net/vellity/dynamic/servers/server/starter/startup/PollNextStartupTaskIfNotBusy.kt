package net.vellity.dynamic.servers.server.starter.startup

import net.vellity.dynamic.servers.common.http.server.environmentOrDefault
import net.vellity.dynamic.servers.server.starter.engine.ContainerEngine
import net.vellity.dynamic.servers.server.starter.startup.source.StartupTaskSource
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@Component
class PollNextStartupTaskIfNotBusy(
  private val containerEngine: ContainerEngine,
  private val startupTaskSource: StartupTaskSource,
  private val startupTaskService: StartupTaskService
) {
  init {
    Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
      { tick() },
      1,
      1,
      TimeUnit.SECONDS
    )
  }

  private fun tick() {
    if (toManyContainersRunning()) {
      return
    }
    val startupTask = startupTaskSource.getNextStartupTask() ?: return
    logger.info("Polled request ${startupTask.id} of type ${startupTask.templateId}")
    startupTaskService.start(startupTask)
  }

  private fun toManyContainersRunning(): Boolean =
    containerEngine.runningContainersCount() >= MAXIMUM_RUNNING_CONTAINERS

  companion object {
    private val MAXIMUM_RUNNING_CONTAINERS = environmentOrDefault("MAX_RUNNING_CONTAINERS", "10")
      .toInt()
    private val logger = LoggerFactory.getLogger(StartupTaskService::class.java)
  }
}