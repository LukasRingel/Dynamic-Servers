package net.vellity.dynamic.servers.log.viewer.home.dto

import net.vellity.dynamic.servers.server.registry.http.client.dto.ServerStatus

enum class ServerStatusDto {
  STARTING,
  RUNNING,
  STOPPING,
  STOPPED,
  SAVING_LOG,
  SAVING_LOG_FAILED,
  SAVING_LOG_SUCCESS,
  READY_TO_DELETE,
  DELETED;

  companion object {
    fun fromBusinessModel(serverStatus: ServerStatus): ServerStatusDto {
      return when (serverStatus) {
        ServerStatus.STARTING -> STARTING
        ServerStatus.RUNNING -> RUNNING
        ServerStatus.STOPPING -> STOPPING
        ServerStatus.STOPPED -> STOPPED
        ServerStatus.SAVING_LOG -> SAVING_LOG
        ServerStatus.SAVING_LOG_FAILED -> SAVING_LOG_FAILED
        ServerStatus.SAVING_LOG_SUCCESS -> SAVING_LOG_SUCCESS
        ServerStatus.READY_TO_DELETE -> READY_TO_DELETE
        ServerStatus.DELETED -> DELETED
      }
    }
  }
}