plugins {
  kotlin("jvm") version "1.9.21"
  id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "net.vellity"
version = "1.0-SNAPSHOT"

allprojects {
  plugins.apply("java")
  plugins.apply("kotlin")
  plugins.apply("java-library")
  plugins.apply("maven-publish")
  plugins.apply("com.github.johnrengelman.shadow")
  plugins.apply("kotlin-kapt")

  repositories {
    mavenCentral()
    mavenLocal()
    maven {
      name = "papermc"
      url = uri("https://repo.papermc.io/repository/maven-public/")
    }
  }

  tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
      jvmTarget = "21"
    }
  }

  kotlin {
    jvmToolchain(21)
  }
}