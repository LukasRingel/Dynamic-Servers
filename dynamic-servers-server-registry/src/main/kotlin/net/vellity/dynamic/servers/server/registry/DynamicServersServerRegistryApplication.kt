package net.vellity.dynamic.servers.server.registry

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import java.time.Clock
import java.time.Instant

@EnableMongoRepositories
@SpringBootApplication(scanBasePackages = ["net.vellity.dynamic.servers"])
open class DynamicServersServerRegistryApplication

fun main(args: Array<String>) {
  runApplication<DynamicServersServerRegistryApplication>(*args) {
    setBannerMode(Banner.Mode.OFF)
  }
}