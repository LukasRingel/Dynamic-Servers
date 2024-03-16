package net.vellity.dynamic.servers.log.agent.engine

interface DockerEngineConfiguration {
  fun hostname(): String

  fun verifyTsl(): Boolean

  fun registryUrl(): String

  fun registryUsername(): String

  fun registryPassword(): String
}