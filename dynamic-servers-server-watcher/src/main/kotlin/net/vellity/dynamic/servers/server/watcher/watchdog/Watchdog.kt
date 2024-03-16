package net.vellity.dynamic.servers.server.watcher.watchdog

import net.vellity.dynamic.servers.server.watcher.container.watcher.ContainerWatcher
import net.vellity.dynamic.servers.server.watcher.logs.watcher.LogStreamWatcher
import org.slf4j.LoggerFactory

class Watchdog(
  private val containerId: String,
  private val logStreamWatcher: LogStreamWatcher,
  private val containerWatcher: ContainerWatcher
) {
  private val logStreamThread: Thread = Thread(logStreamWatcher).apply {
    name = "$containerId-logstream"
    isDaemon = true
  }

  private val containerWatcherThread: Thread = Thread(containerWatcher).apply {
    name = "$containerId-container"
    isDaemon = true
  }

  fun start() {
    logStreamThread.start()
    containerWatcherThread.start()
    logger.info("Started watchdogs for container $containerId")
  }

  fun isRunning(): Boolean =
    logStreamThread.isAlive && containerWatcherThread.isAlive

  fun stop() {
    logStreamWatcher.stop()
    containerWatcher.stop()

    logStreamThread.interrupt()
    containerWatcherThread.interrupt()

    logger.info("Stopped watchdogs for container $containerId")
  }

  companion object {
    private val logger = LoggerFactory.getLogger(Watchdog::class.java)
  }
}