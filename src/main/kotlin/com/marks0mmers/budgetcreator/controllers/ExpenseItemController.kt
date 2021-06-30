package com.marks0mmers.budgetcreator.controllers

import com.marks0mmers.budgetcreator.models.views.ExpenseItemSubmissionView
import com.marks0mmers.budgetcreator.services.ExpenseItemService
import com.marks0mmers.budgetcreator.util.fail
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

object ExpenseItemController {
    private val expenseItemService = ExpenseItemService

    fun Route.expenseItemRoutes() {
        route("/expenseItems") {
            authenticate {
                get {
                    val budgetId = call.request.queryParameters["budgetId"]?.toIntOrNull()
                        ?: fail("Request missing budget Id")
                    val expenseItems = expenseItemService.getAllExpenseItemsForBudget(budgetId)
                    call.respond(expenseItems)
                }

                post {
                    val body = call.receive<ExpenseItemSubmissionView>()
                    val createdExpenseItem = expenseItemService.createExpenseItem(body)
                    call.respond(createdExpenseItem)
                }

                route("/{expenseItemId}") {
                    put {
                        val body = call.receive<ExpenseItemSubmissionView>()
                        val expenseItemId = call.parameters["expenseItemId"]?.toIntOrNull()
                            ?: fail("Invalid Expense Item ID")
                        val updatedExpenseItem = expenseItemService.updateExpenseItem(expenseItemId, body)
                        call.respond(updatedExpenseItem)
                    }

                    delete {
                        val expenseItemId = call.parameters["expenseItemId"]?.toIntOrNull()
                            ?: fail("Invalid Expense Item ID")
                        val deletedExpenseItem = expenseItemService.deleteExpenseItem(expenseItemId)
                        call.respond(deletedExpenseItem)
                    }
                }
            }
        }
    }
}