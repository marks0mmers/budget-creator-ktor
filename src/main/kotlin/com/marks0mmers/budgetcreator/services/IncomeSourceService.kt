package com.marks0mmers.budgetcreator.services

import com.marks0mmers.budgetcreator.models.dto.BudgetDto
import com.marks0mmers.budgetcreator.models.dto.IncomeSourceDto
import com.marks0mmers.budgetcreator.models.persistent.IncomeSource
import com.marks0mmers.budgetcreator.models.views.IncomeSourceSubmissionView
import com.marks0mmers.budgetcreator.repositories.BudgetRepository
import com.marks0mmers.budgetcreator.repositories.IncomeSourceRepository
import com.marks0mmers.budgetcreator.util.BudgetCreatorException
import com.marks0mmers.budgetcreator.util.fail
import io.ktor.http.HttpStatusCode.Companion.NotFound

object IncomeSourceService {
    private val incomeSourceRepository = IncomeSourceRepository

    suspend fun addIncomeSourceToBudget(budgetId: Int, incomeSource: IncomeSourceSubmissionView): BudgetDto {
        return incomeSourceRepository.create(budgetId, incomeSource)
            ?: fail("Cannot find budget $budgetId", NotFound)
    }

    suspend fun removeIncomeSourceFromBudget(incomeSourceId: Int): BudgetDto {
        return incomeSourceRepository.delete(incomeSourceId)
            ?: fail("Cannot find income source $incomeSourceId")
    }

    suspend fun updateIncomeSource(
        incomeSourceId: Int,
        incomeSource: IncomeSourceSubmissionView
    ): BudgetDto {
        return incomeSourceRepository.update(incomeSourceId, incomeSource)
            ?: fail("Cannot find income source $incomeSourceId")
    }
}