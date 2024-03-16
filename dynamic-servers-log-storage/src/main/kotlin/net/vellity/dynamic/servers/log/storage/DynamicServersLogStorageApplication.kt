package net.vellity.dynamic.servers.log.storage

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@EnableMongoRepositories
@SpringBootApplication(scanBasePackages = ["net.vellity.dynamic.servers"])
open class DynamicServersLogStorageApplication

fun main(args: Array<String>) {
  runApplication<DynamicServersLogStorageApplication>(*args) {
    setBannerMode(Banner.Mode.OFF)
  }
}