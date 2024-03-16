package net.vellity.dynamic.servers.log.storage.storage

interface LogStorage {
  fun storeLog(name: String, content: ByteArray)

  fun getLog(name: String): ByteArray?
}