plugins {
  id("org.springframework.boot") version "3.2.0"
  id("io.spring.dependency-management") version "1.1.4"
}

group = "net.vellity.dynamic-servers"
version = "1.0-SNAPSHOT"

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation(project(":dynamic-servers-common-http-server"))

  // docker container engine
  implementation("com.github.docker-java:docker-java:3.3.4")
  implementation("com.github.docker-java:docker-java-transport-zerodep:3.3.4")

  // tcp server
  implementation(project(":dynamic-servers-log-agent-protocol"))
}