import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktorVersion = "1.6.1"
val exposedVersion = "0.32.1"
val jUnitJupiterVersion = "5.7.2"
val mockkVersion = "1.12.0"
val logbackClassicVersion = "1.2.4"
val ktorFlywayFeatureVersion = "1.3.0"
val hikariCPVersion = "5.0.0"
val flywayCoreVersion = "7.11.3"
val kotlinxDatetimeVersion = "0.2.1"
val jbCryptVersion = "0.4"
val postgresqlVersion = "42.2.23"
val kGraphQLVersion = "0.17.11"

plugins {
    kotlin("jvm") version "1.5.20"
    kotlin("plugin.serialization") version "1.5.20"
    application
}

group = "com.marks0mmers"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test-junit5"))
    testImplementation( "org.junit.jupiter",        "junit-jupiter-api",        jUnitJupiterVersion)
    testImplementation( "io.ktor",                  "ktor-server-test-host",    ktorVersion)
    testImplementation( "io.mockk",                 "mockk",                    mockkVersion)
    testRuntimeOnly(    "org.junit.jupiter",        "junit-jupiter-engine",     jUnitJupiterVersion)

    implementation(     "io.ktor",                  "ktor-server-cio",          ktorVersion)
    implementation(     "io.ktor",                  "ktor-serialization",       ktorVersion)
    implementation(     "io.ktor",                  "ktor-auth-jwt",            ktorVersion)

    implementation(     "org.jetbrains.exposed",    "exposed-core",             exposedVersion)
    implementation(     "org.jetbrains.exposed",    "exposed-dao",              exposedVersion)
    implementation(     "org.jetbrains.exposed",    "exposed-jdbc",             exposedVersion)
    implementation(     "org.jetbrains.exposed",    "exposed-java-time",        exposedVersion)

    implementation(     "com.apurebase",            "kgraphql",                 kGraphQLVersion)
    implementation(     "com.apurebase",            "kgraphql-ktor",            kGraphQLVersion)
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
    kotlinOptions.jvmTarget = JavaVersion.VERSION_14.majorVersion
}

application {
    mainClass.set("io.ktor.server.cio.EngineMain")
}