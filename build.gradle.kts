import org.jetbrains.kotlin.gradle.idea.tcs.extras.sourcesClasspathKey

plugins {
    kotlin("jvm") version "2.0.20"
}

group = "io.github.nolanbarry"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(19)
}

tasks {
    task<JavaExec>("day1") {
        dependsOn("build")
        classpath = java.sourceSets["main"].runtimeClasspath
        mainClass = "io.github.nolanbarry.aoc2024.day1.MainKt"
    }
}