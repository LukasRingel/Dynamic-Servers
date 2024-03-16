package net.vellity.dynamic.servers.server.starter.engine

interface ContainerEngine {
  fun createContainer(
    containerName: String,
    containerImage: String,
    environment: Map<String, String>,
    port: Int,
    templateId: String,
    tags: List<String>,
    maximumMemoryInMB: Int
  ): String

  fun startContainer(container: String)

  fun stopContainer(containerName: String)

  fun runningContainersCount(): Int
}