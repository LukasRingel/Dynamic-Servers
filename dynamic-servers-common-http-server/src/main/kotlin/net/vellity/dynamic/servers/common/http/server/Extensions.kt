package net.vellity.dynamic.servers.common.http.server

import java.time.Clock
import java.time.Instant

fun environmentOrDefault(key: String, defaultValue: String): String {
  return if (System.getenv().containsKey(key)) {
    System.getenv(key)
  } else {
    defaultValue
  }
}

fun utcNow(): Instant = Clock.systemUTC().instant()