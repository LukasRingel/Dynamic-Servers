package net.vellity.dynamic.servers.server.watcher.container

import org.springframework.stereotype.Component

@Component
class CachedContainerService(private val containerEngine: ContainerEngine) : ContainerService {
  override fun isContainerRunning(containerName: String): Boolean {
    return containerEngine.isContainerRunning(containerName)
  }

  override fun hasContainerStopped(containerName: String): Boolean {
    return containerEngine.hasContainerStopped(containerName)
  }

  override fun anyContainerWithName(containerName: String): Boolean {
    return containerEngine.anyContainerWithName(containerName)
  }

  override fun stopContainer(containerName: String) {
    containerEngine.stopContainer(containerName)
  }

  override fun deleteContainer(containerName: String) {
    containerEngine.deleteContainer(containerName)
  }

  override fun streamLogsForContainer(containerName: String, consumer: (ByteArray) -> Unit) {
    return containerEngine.streamLogsForContainer(containerName, consumer)
  }
}