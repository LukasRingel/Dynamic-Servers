package net.vellity.dynamic.servers.server.watcher.logs.watcher

import net.vellity.dynamic.servers.server.watcher.logs.collector.LogCollectorService
import net.vellity.dynamic.servers.server.watcher.watchdog.Watcher
import java.util.concurrent.atomic.AtomicBoolean

class LogStreamWatcher(
  private val containerId: String,
  private val logCollector: LogCollectorService,
  private val isContainerReady: (line: String) -> Boolean,
  private val onContainerReady: (instance: LogStreamWatcher) -> Unit = {},
  private val isContainerStartingToStop: (line: String) -> Boolean,
  private val onContainerStartToStop: () -> Unit = {}
) : Watcher {
  private val running = AtomicBoolean(true)
  private val containsErrors = AtomicBoolean(false)

  override fun stop() {
    running.set(false)
  }

  override fun run() {
    while (running.get()) {
      logCollector.streamLogsForServer(containerId) { line ->
        val lineAsString = String(line)

        // if the line is the ready line, call the onContainerReady function
        if (isContainerReady(lineAsString)) {
          onContainerReady(this)
          return@streamLogsForServer
        }

        // if the line is the starting to stop line, call the onContainerStartToStop function
        if (isContainerStartingToStop(lineAsString)) {
          onContainerStartToStop()
          return@streamLogsForServer
        }

        // if the line contains an error, set the containsErrors flag to true
        if (isExceptionLine(lineAsString)) {
          containsErrors.set(true)
        }
      }
    }
  }

  private fun isExceptionLine(line: String): Boolean =
    line.lowercase().contains("caused by: ") ||
      line.lowercase().contains("error]")


  fun detectedAnyExceptions() = containsErrors.get()
}