package com.marks0mmers.budgetcreator.controllers

import com.marks0mmers.budgetcreator.models.views.ExpenseSourceSubmissionView
import com.marks0mmers.budgetcreator.services.ExpenseSourceService
import com.marks0mmers.budgetcreator.util.fail
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

object ExpenseSourceController {
    private val expenseSourceService = ExpenseSourceService

    fun Route.expenseSourceRoutes() {
        route("/expenseSource") {
            post {
                val budgetId = call.parameters["budgetId"]?.toIntOrNull() ?: fail("Invalid Budget ID")
                val body = call.receive<ExpenseSourceSubmissionView>()
                val addedExpenseSource = expenseSourceService.addExpenseSourceToBudget(budgetId, body)
                call.respond(addedExpenseSource)
            }

            route("/{expenseSourceId}") {
                put {
                    val expenseSourceId = call.parameters["expenseSourceId"]?.toIntOrNull()
                        ?: fail("Invalid Expense Source Id")
                    val body = call.receive<ExpenseSourceSubmissionView>()
                    val updatedExpenseSource = expenseSourceService.updateExpenseSource(expenseSourceId, body)
                    call.respond(updatedExpenseSource)
                }

                delete {
                    val expenseSourceId = call.parameters["expenseSourceId"]?.toIntOrNull()
                        ?: fail("Invalid Expense Source Id")
                    val removedExpenseSource = expenseSourceService.removeExpenseSourceFromBudget(expenseSourceId)
                    call.respond(removedExpenseSource)
                }
            }
        }
    }
}