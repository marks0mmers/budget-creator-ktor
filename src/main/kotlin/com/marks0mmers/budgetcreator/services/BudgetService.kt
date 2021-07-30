package com.marks0mmers.budgetcreator.services

import com.marks0mmers.budgetcreator.models.dto.BudgetDto
import com.marks0mmers.budgetcreator.models.views.BudgetSubmissionView
import com.marks0mmers.budgetcreator.repositories.BudgetRepository
import com.marks0mmers.budgetcreator.util.fail

object BudgetService {
    private val budgetRepository = BudgetRepository

    suspend fun getAllBudgetItemsForUser(userId: Int): List<BudgetDto> {
        return budgetRepository
            .findAllByUserId(userId)
    }

    suspend fun createBudgetForUser(budget: BudgetSubmissionView, userId: Int): BudgetDto {
        return budgetRepository
            .create(budget, userId)
    }

    suspend fun updateBudget(budgetId: Int, budgetSubmission: BudgetSubmissionView): BudgetDto {
        return budgetRepository
            .update(budgetId, budgetSubmission)
            ?: fail("Failed to find Budget $budgetId")
    }

    suspend fun deleteBudget(budgetId: Int): BudgetDto {
        return budgetRepository
            .delete(budgetId)
            ?: fail("Failed to find Budget $budgetId")
    }
}