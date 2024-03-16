package net.vellity.dynamic.servers.log.agent

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["net.vellity.dynamic.servers"])
open class DynamicServersLogAgentApplication

fun main(args: Array<String>) {
  runApplication<DynamicServersLogAgentApplication>(*args) {
    setBannerMode(Banner.Mode.OFF)
  }
}