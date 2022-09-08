import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    application
}

group = "me.mainu"
version = "1.0-SNAPSHOT"

val coroutines_version = "1.6.4"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.10")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:$coroutines_version")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:$coroutines_version")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-debug:$coroutines_version")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.+")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}