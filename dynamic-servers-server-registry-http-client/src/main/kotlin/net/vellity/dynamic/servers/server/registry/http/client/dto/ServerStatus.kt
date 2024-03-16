package net.vellity.dynamic.servers.server.registry.http.client.dto

enum class ServerStatus {
  STARTING,
  RUNNING,
  STOPPING,
  STOPPED,
  SAVING_LOG,
  SAVING_LOG_FAILED,
  SAVING_LOG_SUCCESS,
  READY_TO_DELETE,
  DELETED;
}