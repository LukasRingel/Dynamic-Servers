package net.vellity.dynamic.servers.server.watcher.watchdog

interface Watcher : Runnable {
  fun stop()
}