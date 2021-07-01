import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktorVersion = "1.6.0"
val exposedVersion = "0.32.1"
val jUnitJupiterVersion = "5.6.0"
val ktorServerTestHostVersion = "1.3.2"
val mockitoCoreVersion = "3.11.0"
val logbackClassicVersion = "1.2.3"
val ktorFlywayFeatureVersion = "1.2.2"
val hikariCPVersion = "4.0.3"
val flywayCoreVersion = "7.10.0"
val kotlinxDatetimeVersion = "0.2.1"
val jbCryptVersion = "0.4"
val postgresqlVersion = "42.2.20"

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
    testImplementation( "org.junit.jupiter",        "junit-jupiter-api",        jUnitJupiterVersion)
    testImplementation( "io.ktor",                  "ktor-server-test-host",    ktorServerTestHostVersion)
    testImplementation( "org.mockito",              "mockito-core",             mockitoCoreVersion)
    testRuntimeOnly(    "org.junit.jupiter",        "junit-jupiter-engine",     jUnitJupiterVersion)

    implementation(     "io.ktor",                  "ktor-server-netty",        ktorVersion)
    implementation(     "io.ktor",                  "ktor-serialization",       ktorVersion)
    implementation(     "io.ktor",                  "ktor-auth-jwt",            ktorVersion)

    implementation(     "org.jetbrains.exposed",    "exposed-core",             exposedVersion)
    implementation(     "org.jetbrains.exposed",    "exposed-dao",              exposedVersion)
    implementation(     "org.jetbrains.exposed",    "exposed-jdbc",             exposedVersion)
    implementation(     "org.jetbrains.exposed",    "exposed-java-time",        exposedVersion)

    implementation(     "ch.qos.logback",           "logback-classic",          logbackClassicVersion)
    implementation(     "com.viartemev",            "ktor-flyway-feature",      ktorFlywayFeatureVersion)
    implementation(     "com.zaxxer",               "HikariCP",                 hikariCPVersion)
    implementation(     "org.flywaydb",             "flyway-core",              flywayCoreVersion)
    implementation(     "org.jetbrains.kotlinx",    "kotlinx-datetime",         kotlinxDatetimeVersion)
    implementation(     "org.mindrot",              "jbcrypt",                  jbCryptVersion)

    runtimeOnly(        "org.postgresql",           "postgresql",               postgresqlVersion)
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