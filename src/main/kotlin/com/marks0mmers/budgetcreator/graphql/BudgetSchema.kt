package com.marks0mmers.budgetcreator.graphql

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.marks0mmers.budgetcreator.models.dto.BudgetDto
import com.marks0mmers.budgetcreator.models.views.BudgetSubmissionView
import com.marks0mmers.budgetcreator.services.BudgetService

object BudgetSchema {
    private val budgetService = BudgetService

    fun SchemaBuilder.budgetSchema() {

        inputType<BudgetSubmissionView> {
            description = "The input of the budget without the identifier"
        }

        type<BudgetDto> {
            description = "Budget object with all the attributes"
        }

        query("budgets") {
            description = "Get all budgets"
            resolver { userId: Int ->
                budgetService.getAllBudgetItemsForUser(userId)
            }
        }

        mutation("createBudget") {
            description = "Create a new budget"
            resolver { budgetInput: BudgetSubmissionView, userId: Int ->
                budgetService.createBudgetForUser(budgetInput, userId)
            }
        }

        mutation("updateBudget") {
            description = "Update a budget"
            resolver { id: Int, budgetInput: BudgetSubmissionView ->
                budgetService.updateBudget(id, budgetInput)
            }
        }

        mutation("deleteBudget") {
            description = "Delete a budget"
            resolver { id: Int ->
                budgetService.deleteBudget(id)
            }
        }
    }
}
