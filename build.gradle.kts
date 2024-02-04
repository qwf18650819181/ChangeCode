plugins {
  id("java")
  id("org.jetbrains.kotlin.jvm") version "1.9.20"
  id("org.jetbrains.intellij") version "1.16.0"
}

group = "com.wanzi"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://maven.aliyun.com/repository/public") // 阿里云 Maven 镜像
    maven("https://maven.aliyun.com/repository/jcenter") // 阿里云 JCenter 镜像
    maven("https://maven.aliyun.com/repository/google") // 阿里云 Google 镜像
    maven("https://maven.aliyun.com/repository/gradle-plugin") // 阿里云 Gradle Plugin 镜像
    maven("https://maven.aliyun.com/nexus/content/groups/public/")
    maven("https://repo.spring.io/release")
    maven("https://repo.spring.io/milestone")
    maven("https://repo.spring.io/snapshot")
    gradlePluginPortal()
    mavenCentral()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
  version.set("2023.1.5")
  type.set("IC") // Target IDE Platform

  plugins.set(listOf(/* Plugin Dependencies */))
}

tasks {
  // Set the JVM compatibility versions
  withType<JavaCompile> {
    sourceCompatibility = "17"
    targetCompatibility = "17"
  }
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
  }

  patchPluginXml {
    sinceBuild.set("231")
    untilBuild.set("241.*")
  }

  signPlugin {
    certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
    privateKey.set(System.getenv("PRIVATE_KEY"))
    password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
  }

  publishPlugin {
    token.set(System.getenv("PUBLISH_TOKEN"))
  }
}
