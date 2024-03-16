package net.vellity.dynamic.servers.server.registry.history.update

import net.vellity.dynamic.servers.server.registry.registry.ServerStatus

class Updates {
  companion object {
    fun created(): Update = Update().apply {
      type = UpdateType.STATUS_UPDATE_CREATED
    }

    private fun startingToRunning(): Update = Update().apply {
      type = UpdateType.STATUS_UPDATE_STARTING_TO_RUNNING
    }

    private fun runningToStopping(): Update = Update().apply {
      type = UpdateType.STATUS_UPDATE_RUNNING_TO_STOPPING
    }

    fun removed(): Update = Update().apply {
      type = UpdateType.STATUS_UPDATE_REMOVED
    }

    fun tagsElementAdded(tag: String, source: String): Update = Update().apply {
      type = UpdateType.TAGS_ELEMENT_ADDED
      metaData = mapOf(
        "tag" to tag,
        "source" to source
      )
    }

    private fun stoppingToStopped(): Update = Update().apply {
      type = UpdateType.STATUS_UPDATE_STOPPING_TO_STOPPED
    }

    private fun stoppedToSayingLog(): Update = Update().apply {
      type = UpdateType.STATUS_UPDATE_STOPPED_TO_SAVING_LOG
    }

    private fun savingLogToSavingLogFailed(): Update = Update().apply {
      type = UpdateType.STATUS_UPDATE_SAVING_LOG_TO_SAVING_LOG_FAILED
    }

    private fun savingLogToSavingLogSuccess(): Update = Update().apply {
      type = UpdateType.STATUS_UPDATE_SAVING_LOG_TO_SAVING_LOG_SUCCESS
    }

    private fun toReadyToDelete(): Update = Update().apply {
      type = UpdateType.STATUS_UPDATE_TO_READY_TO_DELETE
    }

    private fun readyToDeleteToDeleted(): Update = Update().apply {
      type = UpdateType.STATUS_UPDATE_READY_TO_DELETE_TO_DELETED
    }

    fun forStatusUpdate(serverStatus: ServerStatus): Update {
      return when (serverStatus) {
        ServerStatus.STARTING -> created()
        ServerStatus.RUNNING -> startingToRunning()
        ServerStatus.STOPPING -> runningToStopping()
        ServerStatus.STOPPED -> stoppingToStopped()
        ServerStatus.SAVING_LOG -> stoppedToSayingLog()
        ServerStatus.SAVING_LOG_FAILED -> savingLogToSavingLogFailed()
        ServerStatus.SAVING_LOG_SUCCESS -> savingLogToSavingLogSuccess()
        ServerStatus.READY_TO_DELETE -> toReadyToDelete()
        ServerStatus.DELETED -> readyToDeleteToDeleted()
      }
    }
  }
}