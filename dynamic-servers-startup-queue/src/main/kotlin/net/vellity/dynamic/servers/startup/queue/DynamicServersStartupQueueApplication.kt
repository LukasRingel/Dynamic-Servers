package net.vellity.dynamic.servers.startup.queue

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.time.Clock
import java.time.Instant

@SpringBootApplication(scanBasePackages = ["net.vellity.dynamic.servers"])
open class DynamicServersStartupQueueApplication

fun main(args: Array<String>) {
  runApplication<DynamicServersStartupQueueApplication>(*args) {
    setBannerMode(Banner.Mode.OFF)
  }
}