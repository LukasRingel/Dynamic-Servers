package net.vellity.dynamic.servers.server.watcher.logs.storage

interface LogStorageService {
  fun storeLog(name: String, content: ByteArray)
}