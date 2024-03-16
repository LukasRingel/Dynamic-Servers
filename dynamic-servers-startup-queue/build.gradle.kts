plugins {
  id("org.springframework.boot") version "3.2.0"
  id("io.spring.dependency-management") version "1.1.4"
}

group = "net.vellity.dynamic-servers"
version = "1.0-SNAPSHOT"

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation(project(":dynamic-servers-common-http-server"))
}