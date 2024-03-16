package net.vellity.dynamic.servers.server.starter.engine

interface DockerEngineConfiguration {
  fun hostname(): String

  fun verifyTsl(): Boolean

  fun registryUrl(): String

  fun registryUsername(): String

  fun registryPassword(): String
}