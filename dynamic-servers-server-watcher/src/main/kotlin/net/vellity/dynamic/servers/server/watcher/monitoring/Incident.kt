package net.vellity.dynamic.servers.server.watcher.monitoring

enum class Incident {
  SERVER_REGISTRY_UNREACHABLE,
  CONTAINER_STARTED_WITH_EXCEPTIONS,
  CONTAINER_NEVER_STARTED,
  CONTAINER_LOG_SAVE_FAILED,
}