package com.marks0mmers.budgetcreator.controllers

import com.marks0mmers.budgetcreator.controllers.ExpenseSubCategoryController.expenseSubCategoryRoutes
import com.marks0mmers.budgetcreator.models.dto.UserDto
import com.marks0mmers.budgetcreator.models.views.ExpenseCategorySubmissionView
import com.marks0mmers.budgetcreator.services.ExpenseCategoryService
import com.marks0mmers.budgetcreator.util.fail
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

/**
 * The API controller for the Expense Category related functionality
 */
object ExpenseCategoryController {
    /** The expense category service containing business logic */
    private val expenseCategoryService = ExpenseCategoryService

    /**
     * The controller routes for expense categories
     */
    fun Route.expenseCategoryRoutes() {
        authenticate {
            route("/expenseCategories") {
                get {
                    val user = call.principal<UserDto>() ?: fail("Cannot get user from request",
                        HttpStatusCode.Unauthorized
                    )
                    val expenseCategories = expenseCategoryService.getExpenseCategoriesForUser(user.id)
                    call.respond(expenseCategories)
                }

                post {
                    val user = call.principal<UserDto>() ?: fail("Cannot get user from request",
                        HttpStatusCode.Unauthorized
                    )
                    val body = call.receive<ExpenseCategorySubmissionView>()
                    val createdExpenseCategory = expenseCategoryService.createExpenseCategory(body, user.id)
                    call.respond(createdExpenseCategory)
                }

                route("/{expenseCategoryId}") {
                    put {
                        val expenseCategoryId = call.parameters["expenseCategoryId"]?.toIntOrNull()
                            ?: fail("Invalid Expense Category ID")
                        val body = call.receive<ExpenseCategorySubmissionView>()
                        val updatedExpenseCategory = expenseCategoryService.updateExpenseCategory(expenseCategoryId, body)
                        call.respond(updatedExpenseCategory)
                    }

                    delete {
                        val expenseCategoryId = call.parameters["expenseCategoryId"]?.toIntOrNull()
                            ?: fail("Invalid Expense Category ID")
                        val deletedExpenseCategory = expenseCategoryService.deleteExpenseCategory(expenseCategoryId)
                        call.respond(deletedExpenseCategory)
                    }

                    expenseSubCategoryRoutes()
                }
            }
        }
    }
}