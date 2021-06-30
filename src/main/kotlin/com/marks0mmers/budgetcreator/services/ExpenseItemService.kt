package com.marks0mmers.budgetcreator.services

import com.marks0mmers.budgetcreator.models.dto.ExpenseItemDto
import com.marks0mmers.budgetcreator.models.views.ExpenseItemSubmissionView
import com.marks0mmers.budgetcreator.repositories.ExpenseItemRepository
import com.marks0mmers.budgetcreator.util.fail

object ExpenseItemService {
    private val expenseItemRepository = ExpenseItemRepository

    suspend fun getAllExpenseItemsForBudget(budgetId: Int): List<ExpenseItemDto> {
        return expenseItemRepository
            .findAllByBudgetId(budgetId)
    }

    suspend fun createExpenseItem(expenseItem: ExpenseItemSubmissionView): ExpenseItemDto {
        return expenseItemRepository
            .create(expenseItem)
    }

    suspend fun updateExpenseItem(expenseItemId: Int, expenseItem: ExpenseItemSubmissionView): ExpenseItemDto {
        return expenseItemRepository
            .update(expenseItemId, expenseItem)
            ?: fail("Failed to find Expense Item $expenseItemId")
    }

    suspend fun deleteExpenseItem(expenseItemId: Int): ExpenseItemDto {
        return expenseItemRepository
            .delete(expenseItemId)
            ?: fail("Failed to find Expense Item $expenseItemId")
    }
}