package com.marks0mmers.budgetcreator.graphql

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.marks0mmers.budgetcreator.models.dto.ExpenseSourceDto
import com.marks0mmers.budgetcreator.models.views.ExpenseSourceSubmissionView
import com.marks0mmers.budgetcreator.services.ExpenseSourceService

object ExpenseSourceSchema {
    private val expenseSourceService = ExpenseSourceService

    fun SchemaBuilder.expenseSourceSchema() {
        inputType<ExpenseSourceSubmissionView> {
            description = "The input object for Expense Sources"
        }

        type<ExpenseSourceDto> {
            description = "The Expense Source object"
        }

        query("expenseSources") {
            description = "Get all Expense Sources by Budget"
            resolver { budgetId: Int ->
                expenseSourceService.getExpenseSourcesByBudget(budgetId)
            }
        }

        mutation("createExpenseSource") {
            description = "Create an expense source"
            resolver { budgetId: Int, expenseSourceInput: ExpenseSourceSubmissionView ->
                expenseSourceService.addExpenseSourceToBudget(budgetId, expenseSourceInput)
            }
        }

        mutation("updateExpenseSource") {
            description = "Update an Expense Source"
            resolver { id: Int, expenseSourceInput: ExpenseSourceSubmissionView ->
                expenseSourceService.updateExpenseSource(id, expenseSourceInput)
            }
        }

        mutation("deleteExpenseSource") {
            description = "Delete an Expense Source"
            resolver { id: Int ->
                expenseSourceService.removeExpenseSourceFromBudget(id)
            }
        }
    }
}