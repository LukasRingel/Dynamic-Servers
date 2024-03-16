package net.vellity.dynamic.servers.log.viewer

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["net.vellity.dynamic.servers"])
open class DynamicServersLogViewerApplication

fun main(args: Array<String>) {
  runApplication<DynamicServersLogViewerApplication>(*args) {
    setBannerMode(Banner.Mode.OFF)
  }
}