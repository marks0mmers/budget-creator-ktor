package com.marks0mmers.budgetcreator.services

import com.marks0mmers.budgetcreator.models.dto.IncomeItemDto
import com.marks0mmers.budgetcreator.models.views.IncomeItemSubmissionView
import com.marks0mmers.budgetcreator.repositories.IncomeItemRepository
import com.marks0mmers.budgetcreator.util.fail

object IncomeItemService {
    private val incomeItemRepository = IncomeItemRepository

    suspend fun getAllIncomeItemsForBudget(budgetId: Int): List<IncomeItemDto> {
        return incomeItemRepository
            .findAllByBudgetId(budgetId)
    }

    suspend fun createIncomeItem(incomeItem: IncomeItemSubmissionView): IncomeItemDto {
        return incomeItemRepository
            .create(incomeItem)
    }

    suspend fun updateIncomeItem(incomeItemId: Int, incomeItem: IncomeItemSubmissionView): IncomeItemDto {
        return incomeItemRepository
            .update(incomeItemId, incomeItem)
            ?: fail("Failed to find Income Item $incomeItemId")
    }

    suspend fun deleteIncomeItem(incomeItemId: Int): IncomeItemDto {
        return incomeItemRepository
            .delete(incomeItemId)
            ?: fail("Failed to find Income Item $incomeItemId")
    }
}