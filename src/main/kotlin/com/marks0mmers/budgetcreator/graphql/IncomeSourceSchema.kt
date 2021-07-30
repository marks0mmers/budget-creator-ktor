package com.marks0mmers.budgetcreator.graphql

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.marks0mmers.budgetcreator.models.dto.IncomeSourceDto
import com.marks0mmers.budgetcreator.models.views.IncomeSourceSubmissionView
import com.marks0mmers.budgetcreator.services.IncomeSourceService

object IncomeSourceSchema {
    private val incomeSourceService = IncomeSourceService

    fun SchemaBuilder.incomeSourceSchema() {
        inputType<IncomeSourceSubmissionView> {
            description = "The input of an income source without the id"
        }

        type<IncomeSourceDto> {
            description = "The Income Source Object with all the attributes"
        }

        query("incomeSources") {
            description = "Get Income Sources for a Budget"
            resolver { budgetId: Int ->
                incomeSourceService.getIncomeSourcesByBudget(budgetId)
            }
        }

        mutation("createIncomeSource") {
            description = "Creates an Income Source"
            resolver { budgetId: Int, incomeSourceInput: IncomeSourceSubmissionView ->
                incomeSourceService.addIncomeSourceToBudget(budgetId, incomeSourceInput)
            }
        }

        mutation("updateIncomeSource") {
            description = "Updates an Income Source"
            resolver { id: Int, incomeSourceInput: IncomeSourceSubmissionView ->
                incomeSourceService.updateIncomeSource(id, incomeSourceInput)
            }
        }

        mutation("deleteIncomeSource") {
            description = "Delete an Income Source"
            resolver { id: Int ->
                incomeSourceService.removeIncomeSourceFromBudget(id)
            }
        }
    }
}