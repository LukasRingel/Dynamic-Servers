package net.vellity.dynamic.servers.templates

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.scheduling.concurrent.CustomizableThreadFactory
import java.time.Clock
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@EnableMongoRepositories
@SpringBootApplication(scanBasePackages = ["net.vellity.dynamic.servers"])
open class DynamicServersControllerApplication

fun main(args: Array<String>) {
  runApplication<DynamicServersControllerApplication>(*args) {
    setBannerMode(Banner.Mode.OFF)
  }
}