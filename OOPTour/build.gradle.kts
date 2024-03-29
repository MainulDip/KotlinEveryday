import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    kotlin("plugin.serialization") version "1.7.20"
    application
}

group = "me.mainu"
version = "1.0-SNAPSHOT"

val coroutines_version = "1.6.4"

repositories {
    mavenCentral()
}

dependencies {
    implementation("junit:junit:4.13.1")
    implementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.10")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:$coroutines_version")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:$coroutines_version")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-debug:$coroutines_version")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.+")

    testImplementation(platform("org.junit:junit-bom:5.9.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")

    // Serialization/Data Storage
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    // protobuf
    implementation("com.google.protobuf:protobuf-java:3.21.9")
}

tasks.test {
    useJUnitPlatform()

//    testLogging {
//        events("passed", "skipped", "failed")
//    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Test> {
    useJUnitPlatform()
    jvmArgs =  mutableListOf("--enable-preview")
}

application {
    mainClass.set("MainKt")
}