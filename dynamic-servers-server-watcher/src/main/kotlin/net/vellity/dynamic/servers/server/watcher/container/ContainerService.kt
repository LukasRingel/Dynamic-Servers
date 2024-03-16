package net.vellity.dynamic.servers.server.watcher.container

interface ContainerService {
  fun isContainerRunning(containerName: String): Boolean

  fun hasContainerStopped(containerName: String): Boolean

  fun anyContainerWithName(containerName: String): Boolean

  fun stopContainer(containerName: String)

  fun deleteContainer(containerName: String)

  fun streamLogsForContainer(containerName: String, consumer: (ByteArray) -> Unit)
}