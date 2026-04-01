plugins {
    kotlin("jvm") version "2.0.21"
    application
}

group = "io.pageindex.demo"
version = "1.0.0"

application {
    mainClass.set("io.pageindex.demo.ApplicationKt")
}

repositories {
    mavenCentral()
}

val ktorVersion = "3.0.2"

dependencies {
    // PageIndex library (resolved via composite build from root project)
    implementation("io.pageindex:pageindex-jvm:0.1.0")

    // Ktor server
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-server-status-pages:$ktorVersion")
    implementation("io.ktor:ktor-server-cors:$ktorVersion")
    implementation("io.ktor:ktor-serialization-jackson:$ktorVersion")

    // Jackson Kotlin module
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.2")

    // Logging
    implementation("ch.qos.logback:logback-classic:1.5.12")
}

kotlin {
    jvmToolchain(21)
}
