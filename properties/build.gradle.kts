import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    id("org.springframework.boot") version "3.2.2" apply false
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
}

dependencies {
    compileOnly(libs.org.springframework.boot.spring.boot.starter.web)
    compileOnly(libs.org.springframework.boot.spring.boot.starter.cache)
    compileOnly(libs.org.springframework.boot.spring.boot.starter.data.jdbc)
    compileOnly(libs.org.springframework.boot.spring.boot.starter.validation)
    compileOnly(libs.kotlin.stdlib)
    compileOnly(libs.kotlin.reflect)
    compileOnly(libs.jakarta.validation.api)
    compileOnly(libs.jackson.module.kotlin)

}

description = "properties"
repositories {
    mavenCentral()
}
kotlin {
    jvmToolchain(17)
}
tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
    }
}
