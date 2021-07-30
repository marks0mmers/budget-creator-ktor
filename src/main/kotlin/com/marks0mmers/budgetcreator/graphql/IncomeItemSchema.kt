package com.marks0mmers.budgetcreator.graphql

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.apurebase.kgraphql.schema.dsl.SchemaConfigurationDSL
import com.marks0mmers.budgetcreator.models.dto.IncomeItemDto
import com.marks0mmers.budgetcreator.models.views.IncomeItemSubmissionView
import com.marks0mmers.budgetcreator.services.IncomeItemService

object IncomeItemSchema {
    private val incomeItemService = IncomeItemService

    fun SchemaBuilder.incomeItemSchema() {
        inputType<IncomeItemSubmissionView> {
            description = "The input object for Income Items"
        }

        type<IncomeItemDto> {
            description = "The Income Item Object"
        }

        query("incomeItems") {
            description = "Get Income Items for Budget"
            resolver { budgetId: Int ->
                incomeItemService.getAllIncomeItemsForBudget(budgetId)
            }
        }

        mutation("createIncomeItem") {
            description = "Create an Income Item"
            resolver { incomeItemInput: IncomeItemSubmissionView ->
                incomeItemService.createIncomeItem(incomeItemInput)
            }
        }

        mutation("updateIncomeItem") {
            description = "Update an Income Item"
            resolver { id: Int, incomeItemInput: IncomeItemSubmissionView ->
                incomeItemService.updateIncomeItem(id, incomeItemInput)
            }
        }

        mutation("deleteIncomeItem") {
            description = "Delete an Income Item"
            resolver { id: Int ->
                incomeItemService.deleteIncomeItem(id)
            }
        }
    }
}