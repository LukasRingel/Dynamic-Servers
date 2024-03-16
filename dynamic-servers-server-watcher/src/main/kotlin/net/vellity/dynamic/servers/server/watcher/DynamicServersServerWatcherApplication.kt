package net.vellity.dynamic.servers.server.watcher

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["net.vellity.dynamic.servers"])
open class DynamicServersServerWatcherApplication

fun main(args: Array<String>) {
  runApplication<DynamicServersServerWatcherApplication>(*args) {
    setBannerMode(Banner.Mode.OFF)
  }
}