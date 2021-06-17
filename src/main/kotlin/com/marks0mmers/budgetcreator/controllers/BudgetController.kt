package com.marks0mmers.budgetcreator.controllers

import com.marks0mmers.budgetcreator.controllers.ExpenseSourceController.expenseSourceRoutes
import com.marks0mmers.budgetcreator.controllers.IncomeSourceController.incomeSourceRoutes
import com.marks0mmers.budgetcreator.models.dto.UserDto
import com.marks0mmers.budgetcreator.models.views.BudgetSubmissionView
import com.marks0mmers.budgetcreator.services.BudgetService
import com.marks0mmers.budgetcreator.util.fail
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.HttpStatusCode.Companion.Unauthorized
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

/**
 * The API Controller for Budget related Functionality
 */
object BudgetController {
    /** The budget service containing business logic */
    private val budgetService = BudgetService

    /**
     * The routes for the budget controller
     */
    fun Route.budgetRoutes() {
        route("/budgets") {
            authenticate {
                get {
                    val user = call.principal<UserDto>() ?: fail("Cannot get user from request", Unauthorized)
                    val budgets = budgetService.getAllBudgetItemsForUser(user.id)
                    call.respond(budgets)
                }

                post {
                    val user = call.principal<UserDto>() ?: fail("Cannot get user from request", Unauthorized)
                    val body = call.receive<BudgetSubmissionView>()
                    val createdBudget = budgetService.createBudgetForUser(body, user.id)
                    call.respond(createdBudget)
                }

                route("/{budgetId}") {
                    put {
                        val body = call.receive<BudgetSubmissionView>()
                        val budgetId = call.parameters["budgetId"]?.toIntOrNull() ?: fail("Invalid Budget ID")
                        val updatedBudget = budgetService.updateBudget(budgetId, body)
                        call.respond(updatedBudget)
                    }

                    delete {
                        val budgetId = call.parameters["budgetId"]?.toIntOrNull() ?: fail("Invalid Budget ID")
                        val deletedBudget = budgetService.deleteBudget(budgetId)
                        call.respond(deletedBudget)
                    }

                    incomeSourceRoutes()
                    expenseSourceRoutes()
                }
            }
        }
    }
}