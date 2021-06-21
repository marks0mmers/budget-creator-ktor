package com.marks0mmers.budgetcreator.controllers

import com.marks0mmers.budgetcreator.models.views.ExpenseCategorySubmissionView
import com.marks0mmers.budgetcreator.services.ExpenseSubCategoryService
import com.marks0mmers.budgetcreator.util.fail
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

/**
 * The API Controller for Expense Sub-category functionality
 */
object ExpenseSubCategoryController {
    /** The expense sub-category service containing business logic */
    private val expenseSubCategoryService = ExpenseSubCategoryService

    /**
     * The controller routes for the Expense sub-categories
     */
    fun Route.expenseSubCategoryRoutes() {
        route("/expenseSubCategory") {
            post {
                val body = call.receive<ExpenseCategorySubmissionView>()
                val expenseCategoryId = call.parameters["expenseCategoryId"]?.toIntOrNull()
                    ?: fail("Invalid Expense Category ID")
                val createdExpenseSubCategory = expenseSubCategoryService.addExpenseSubCategoryToExpenseCategory(
                    expenseCategoryId,
                    body
                )
                call.respond(createdExpenseSubCategory)
            }

            route("/{expenseSubCategoryId}") {
                put {
                    val body = call.receive<ExpenseCategorySubmissionView>()
                    val expenseSubCategoryId = call.parameters["expenseSubCategoryId"]?.toIntOrNull()
                        ?: fail("Invalid Expense Sub-category ID")
                    val updatedExpenseSubCategory = expenseSubCategoryService.updateExpenseSubCategory(
                        expenseSubCategoryId,
                        body
                    )
                    call.respond(updatedExpenseSubCategory)
                }

                delete {
                    val expenseSubCategoryId = call.parameters["expenseSubCategoryId"]?.toIntOrNull()
                        ?: fail("Invalid Expense Sub-category ID")
                    val deletedExpenseSubCategory = expenseSubCategoryService.removeExpenseSubCategoryFromExpenseCategory(
                        expenseSubCategoryId
                    )
                    call.respond(deletedExpenseSubCategory)
                }
            }
        }
    }
}