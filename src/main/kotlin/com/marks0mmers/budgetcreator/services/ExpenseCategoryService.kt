package com.marks0mmers.budgetcreator.services

import com.marks0mmers.budgetcreator.models.dto.ExpenseCategoryDto
import com.marks0mmers.budgetcreator.models.persistent.ExpenseCategory
import com.marks0mmers.budgetcreator.models.views.ExpenseCategorySubmissionView
import com.marks0mmers.budgetcreator.repositories.ExpenseCategoryRepository
import com.marks0mmers.budgetcreator.util.fail
import com.marks0mmers.budgetcreator.util.BudgetCreatorException
import kotlinx.coroutines.flow.Flow

object ExpenseCategoryService {
    private val expenseCategoryRepository = ExpenseCategoryRepository

    suspend fun getExpenseCategoriesForUser(userId: Int): List<ExpenseCategoryDto> {
        return expenseCategoryRepository
            .findAllByUser(userId)
    }

    suspend fun createExpenseCategory(ec: ExpenseCategorySubmissionView, userId: Int): ExpenseCategoryDto {
        return expenseCategoryRepository
            .create(ec, userId)
    }

    suspend fun updateExpenseCategory(
        expenseCategoryId: Int,
        expenseCategorySubmission: ExpenseCategorySubmissionView
    ): ExpenseCategoryDto {
        return expenseCategoryRepository
            .update(expenseCategoryId, expenseCategorySubmission)
            ?: fail("Failed to update Expense Category $expenseCategoryId")
    }

    suspend fun deleteExpenseCategory(expenseCategoryId: Int): ExpenseCategoryDto {
        return expenseCategoryRepository
            .delete(expenseCategoryId)
            ?: fail("Failed to remove Expense Category $expenseCategoryId")
    }
}