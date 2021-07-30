package com.marks0mmers.budgetcreator.graphql

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.marks0mmers.budgetcreator.models.dto.ExpenseSubCategoryDto
import com.marks0mmers.budgetcreator.models.views.ExpenseCategorySubmissionView
import com.marks0mmers.budgetcreator.services.ExpenseSubCategoryService

object ExpenseSubCategorySchema {
    private val expenseSubCategoryService = ExpenseSubCategoryService

    fun SchemaBuilder.expenseSubCategorySchema() {
        type<ExpenseSubCategoryDto> {
            description = "The Expense Sub-Category Object"
        }

        query("expenseSubCategories") {
            description = "Fetch all Expense Sub-Categories by Expense Category Id"
            resolver { expenseCategoryId: Int ->
                expenseSubCategoryService.getExpenseSubCategoriesByExpenseCategory(expenseCategoryId)
            }
        }

        mutation("createExpenseSubCategory") {
            description = "Create an Expense Sub-Category"
            resolver { expenseCategoryId: Int, expenseSubCategoryInput: ExpenseCategorySubmissionView ->
                expenseSubCategoryService.addExpenseSubCategoryToExpenseCategory(
                    expenseCategoryId,
                    expenseSubCategoryInput
                )
            }
        }

        mutation("updateExpenseSubCategory") {
            description = "Update an Expense Sub-Category"
            resolver { id: Int, expenseSubCategoryInput: ExpenseCategorySubmissionView ->
                expenseSubCategoryService.updateExpenseSubCategory(id,  expenseSubCategoryInput)
            }
        }

        mutation("deleteExpenseSubCategory") {
            description = "Delete an Expense Sub-Category"
            resolver { id: Int ->
                expenseSubCategoryService.removeExpenseSubCategoryFromExpenseCategory(id)
            }
        }
    }
}