import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktorVersion = "1.6.0"
val exposedVersion = "0.32.1"
val jUnitJupiterVersion = "5.6.0"

plugins {
    kotlin("jvm") version "1.5.0"
    kotlin("plugin.serialization") version "1.5.0"
    application
}

group = "com.marks0mmers"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter", "junit-jupiter-api", jUnitJupiterVersion)
    testImplementation("io.ktor", "ktor-server-test-host", "1.3.2")
    testImplementation("org.mockito", "mockito-core", "3.11.0")
    testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine", jUnitJupiterVersion)

    implementation("io.ktor", "ktor-server-netty", ktorVersion)
    implementation("io.ktor", "ktor-serialization", ktorVersion)
    implementation("io.ktor", "ktor-auth-jwt", ktorVersion)

    implementation("org.jetbrains.exposed", "exposed-core", exposedVersion)
    implementation("org.jetbrains.exposed", "exposed-dao", exposedVersion)
    implementation("org.jetbrains.exposed", "exposed-jdbc", exposedVersion)
    implementation("org.jetbrains.exposed", "exposed-java-time", exposedVersion)

    implementation("ch.qos.logback", "logback-classic", "1.2.3")
    implementation("com.viartemev", "ktor-flyway-feature", "1.2.2")
    implementation("com.zaxxer", "HikariCP", "4.0.3")
    implementation("org.flywaydb", "flyway-core", "7.10.0")
    implementation("org.jetbrains.kotlinx", "kotlinx-datetime", "0.2.1")
    implementation("org.mindrot", "jbcrypt", "0.4")

    runtimeOnly("org.postgresql", "postgresql", "42.2.20")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}