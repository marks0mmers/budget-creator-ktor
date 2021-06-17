package com.marks0mmers.budgetcreator.config

import com.marks0mmers.budgetcreator.util.BudgetCreatorException
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*

object ErrorConfig {
    fun Application.errorConfig() {
        install(StatusPages) {
            exception<BudgetCreatorException> { ex ->
                call.respond(ex.status, ex.message)
            }
            exception<Throwable> { ex ->
                call.respond(HttpStatusCode.InternalServerError, ex.message ?: "Internal Server Error")
            }
        }
    }
}