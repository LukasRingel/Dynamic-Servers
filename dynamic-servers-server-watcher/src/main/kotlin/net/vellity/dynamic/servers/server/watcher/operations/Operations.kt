package net.vellity.dynamic.servers.server.watcher.operations

interface Operations {
  fun registerToOperations()

  fun heartbeat()
}