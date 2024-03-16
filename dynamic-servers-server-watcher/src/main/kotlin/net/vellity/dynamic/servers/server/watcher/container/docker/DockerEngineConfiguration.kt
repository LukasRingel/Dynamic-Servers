package net.vellity.dynamic.servers.server.watcher.container.docker

interface DockerEngineConfiguration {
  fun hostname(): String

  fun verifyTsl(): Boolean

  fun registryUrl(): String

  fun registryUsername(): String

  fun registryPassword(): String
}