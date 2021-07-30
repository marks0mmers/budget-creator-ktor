package com.marks0mmers.budgetcreator.graphql

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.marks0mmers.budgetcreator.models.dto.ExpenseItemDto
import com.marks0mmers.budgetcreator.models.views.ExpenseItemSubmissionView
import com.marks0mmers.budgetcreator.services.ExpenseItemService

object ExpenseItemSchema {
    private val expenseItemService = ExpenseItemService

    fun SchemaBuilder.expenseItemSchema() {
        inputType<ExpenseItemSubmissionView> {
            description = "The input object for Expense Items"
        }

        type<ExpenseItemDto> {
            description = "The Expense Item Object"
        }

        query("expenseItems") {
            description = "Get Expense Items for Budget"
            resolver { budgetId: Int ->
                expenseItemService.getAllExpenseItemsForBudget(budgetId)
            }
        }

        mutation("createExpenseItem") {
            description = "Create an Expense Item"
            resolver { expenseItemInput: ExpenseItemSubmissionView ->
                expenseItemService.createExpenseItem(expenseItemInput)
            }
        }

        mutation("updateExpenseItem") {
            description = "Update an Expense Item"
            resolver { id: Int, expenseItemInput: ExpenseItemSubmissionView ->
                expenseItemService.updateExpenseItem(id, expenseItemInput)
            }
        }

        mutation("deleteExpenseItem") {
            description = "Delete an Expense Item"
            resolver { id: Int ->
                expenseItemService.deleteExpenseItem(id)
            }
        }
    }
}