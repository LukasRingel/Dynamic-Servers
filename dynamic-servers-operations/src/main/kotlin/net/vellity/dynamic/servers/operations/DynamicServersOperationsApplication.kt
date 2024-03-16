package net.vellity.dynamic.servers.operations

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.time.Clock
import java.time.Instant

@SpringBootApplication(scanBasePackages = ["net.vellity.dynamic.servers"])
open class DynamicServersOperationsApplication

fun main(args: Array<String>) {
  runApplication<DynamicServersOperationsApplication>(*args) {
    setBannerMode(Banner.Mode.OFF)
  }
}