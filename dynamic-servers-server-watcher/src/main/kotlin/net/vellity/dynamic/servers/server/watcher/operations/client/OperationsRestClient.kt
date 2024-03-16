package net.vellity.dynamic.servers.server.watcher.operations.client

interface OperationsRestClient {
  fun registerToOperations()

  fun heartbeat()
}