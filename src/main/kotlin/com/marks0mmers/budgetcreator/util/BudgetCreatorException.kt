package com.marks0mmers.budgetcreator.util

import io.ktor.http.*

class BudgetCreatorException(override val message: String, val status: HttpStatusCode) : Exception(message)

fun fail(message: String): Nothing {
    throw BudgetCreatorException(message, HttpStatusCode.BadRequest)
}

fun fail(message: String, status: HttpStatusCode): Nothing {
    throw BudgetCreatorException(message, status)
}