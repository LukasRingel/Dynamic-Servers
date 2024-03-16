plugins {
  kotlin("jvm") version "1.9.21"
  id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "net.vellity.dynamic-servers"
version = "1.0-SNAPSHOT"

dependencies {
  // velocity
  compileOnly("com.velocitypowered:velocity-api:3.1.0-SNAPSHOT")
  annotationProcessor("com.velocitypowered:velocity-api:3.3.0-SNAPSHOT")
  kapt("com.velocitypowered:velocity-api:3.2.0-SNAPSHOT")

  // http connection
  implementation(project(":dynamic-servers-server-registry-http-client"))
}