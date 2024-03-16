package net.vellity.dynamic.servers.server.watcher.logs.watcher

import net.vellity.dynamic.servers.server.watcher.logs.collector.LogCollectorService

internal object LogStreamWatcherFactory {
  fun findForTags(
    tags: List<String>,
    containerId: String,
    logCollectorService: LogCollectorService,
    onContainerReady: (instance: LogStreamWatcher) -> Unit,
    onContainerStartToStop: () -> Unit
  ): LogStreamWatcher {
    return when {
      tags.contains("VelocityLogStreamWatcher") -> velocity(
        containerId,
        logCollectorService,
        onContainerReady,
        onContainerStartToStop
      )
      tags.contains("PaperMCLogStreamWatcher") -> paper(
        containerId,
        logCollectorService,
        onContainerReady,
        onContainerStartToStop
      )
      else -> fallback(
        containerId,
        logCollectorService,
        onContainerReady,
        onContainerStartToStop
      )
    }
  }

  private fun paper(
    containerId: String,
    logCollectorService: LogCollectorService,
    onContainerReady: (instance: LogStreamWatcher) -> Unit,
    onContainerStartToStop: () -> Unit
  ) = LogStreamWatcher(
    containerId = containerId,
    logCollector = logCollectorService,
    isContainerReady = { line -> line.contains("Done (") && line.contains("s)! For help, type \"help\"") },
    onContainerReady = onContainerReady,
    isContainerStartingToStop = { line -> line.contains("Stopping server") },
    onContainerStartToStop = onContainerStartToStop
  )

  private fun velocity(
    containerId: String,
    logCollectorService: LogCollectorService,
    onContainerReady: (instance: LogStreamWatcher) -> Unit,
    onContainerStartToStop: () -> Unit
  ) = LogStreamWatcher(
    containerId = containerId,
    logCollector = logCollectorService,
    isContainerReady = { line -> line.contains("Done (") && line.contains("s)!") },
    onContainerReady = onContainerReady,
    isContainerStartingToStop = { line -> line.contains("Shutting down the proxy...") },
    onContainerStartToStop = onContainerStartToStop
  )

  private fun fallback(
    containerId: String,
    logCollectorService: LogCollectorService,
    onContainerReady: (instance: LogStreamWatcher) -> Unit,
    onContainerStartToStop: () -> Unit
  ) = LogStreamWatcher(
    containerId = containerId,
    logCollector = logCollectorService,
    isContainerReady = { true },
    onContainerReady = onContainerReady,
    isContainerStartingToStop = { line -> line.contains("Stopping") },
    onContainerStartToStop = onContainerStartToStop
  )
}