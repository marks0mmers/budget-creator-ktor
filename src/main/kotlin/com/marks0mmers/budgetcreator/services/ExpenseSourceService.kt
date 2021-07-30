package com.marks0mmers.budgetcreator.services

import com.marks0mmers.budgetcreator.models.dto.BudgetDto
import com.marks0mmers.budgetcreator.models.dto.ExpenseSourceDto
import com.marks0mmers.budgetcreator.models.views.ExpenseSourceSubmissionView
import com.marks0mmers.budgetcreator.repositories.ExpenseSourceRepository
import com.marks0mmers.budgetcreator.util.fail
import io.ktor.http.HttpStatusCode.Companion.NotFound

object ExpenseSourceService {
    private val expenseSourceRepository = ExpenseSourceRepository

    suspend fun getExpenseSourcesByBudget(budgetId: Int): List<ExpenseSourceDto> {
        return expenseSourceRepository.findByBudget(budgetId)
    }

    suspend fun addExpenseSourceToBudget(budgetId: Int, expenseSource: ExpenseSourceSubmissionView): BudgetDto {
        return expenseSourceRepository.create(budgetId, expenseSource)
            ?: fail("Cannot find budget $budgetId", NotFound)
    }

    suspend fun removeExpenseSourceFromBudget(expenseSourceId: Int): BudgetDto {
        return expenseSourceRepository.delete(expenseSourceId)
            ?: fail("Cannot find Expense Source $expenseSourceId", NotFound)
    }

    suspend fun updateExpenseSource(
        expenseSourceId: Int,
        expenseSource: ExpenseSourceSubmissionView
    ): BudgetDto {
        return expenseSourceRepository.update(expenseSourceId, expenseSource)
            ?: fail("Cannot fid Expense Source $expenseSourceId", NotFound)
    }
}