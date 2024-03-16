package net.vellity.dynamic.servers.server.starter.engine

import net.vellity.dynamic.servers.common.http.server.environmentOrDefault
import org.springframework.stereotype.Component

@Component
open class EnvironmentDockerEngineConfiguration : DockerEngineConfiguration {
  override fun hostname(): String =
    environmentOrDefault("DOCKER_HOSTNAME", "tcp://localhost:2375")

  override fun verifyTsl(): Boolean =
    environmentOrDefault("DOCKER_TSL", "false").toBoolean()

  override fun registryUrl(): String =
    environmentOrDefault("DOCKER_REGISTRY_URL", "http://localhost:5000")

  override fun registryUsername(): String =
    environmentOrDefault("DOCKER_REGISTRY_USERNAME", "user")

  override fun registryPassword(): String =
    environmentOrDefault("DOCKER_REGISTRY_PASSWORD", "pass")
}