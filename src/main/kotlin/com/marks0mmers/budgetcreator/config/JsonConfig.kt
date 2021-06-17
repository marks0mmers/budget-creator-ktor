package com.marks0mmers.budgetcreator.config

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*
import kotlinx.serialization.json.Json

object JsonConfig {
    fun Application.jsonConfig() {
        val jsonModule = Json {
            prettyPrint = true
        }
        install(ContentNegotiation) {
            json(jsonModule)
        }
    }
}