package net.vellity.dynamic.servers.server.watcher.http.client

interface WatcherClient {
  fun stopServer(serverId: String)
}