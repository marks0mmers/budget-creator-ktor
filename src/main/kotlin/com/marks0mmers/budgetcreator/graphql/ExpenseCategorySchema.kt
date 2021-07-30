package com.marks0mmers.budgetcreator.graphql

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.marks0mmers.budgetcreator.models.dto.ExpenseCategoryDto
import com.marks0mmers.budgetcreator.models.views.ExpenseCategorySubmissionView
import com.marks0mmers.budgetcreator.services.ExpenseCategoryService

object ExpenseCategorySchema {
    private val expenseCategoryService = ExpenseCategoryService

    fun SchemaBuilder.expenseCategorySchema() {
        inputType<ExpenseCategorySubmissionView> {
            description = "Expense Category and Sub-Category Submission Object"
        }

        type<ExpenseCategoryDto> {
            description = "The Expense Category object"
        }

        query("expenseCategories") {
            description = "Gets all Expense Categories for a User"
            resolver { userId: Int ->
                expenseCategoryService.getExpenseCategoriesForUser(userId)
            }
        }

        mutation("createExpenseCategory") {
            description = "Creates an Expense Category"
            resolver { expenseCategoryInput: ExpenseCategorySubmissionView, userId: Int ->
                expenseCategoryService.createExpenseCategory(expenseCategoryInput, userId)
            }
        }

        mutation("updateExpenseCategory") {
            description = "Updates an Expense Category"
            resolver { id: Int, expenseCategoryInput: ExpenseCategorySubmissionView ->
                expenseCategoryService.updateExpenseCategory(id, expenseCategoryInput)
            }
        }

        mutation("deleteExpenseCategory") {
            description = "Deletes an Expense Category"
            resolver { id: Int ->
                expenseCategoryService.deleteExpenseCategory(id)
            }
        }
    }
}