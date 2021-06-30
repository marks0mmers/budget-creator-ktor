package com.marks0mmers.budgetcreator.services

import com.marks0mmers.budgetcreator.models.dto.ExpenseCategoryDto
import com.marks0mmers.budgetcreator.models.persistent.ExpenseSubCategory
import com.marks0mmers.budgetcreator.models.views.ExpenseCategorySubmissionView
import com.marks0mmers.budgetcreator.repositories.ExpenseSubCategoryRepository
import com.marks0mmers.budgetcreator.util.BudgetCreatorException
import com.marks0mmers.budgetcreator.util.fail
import io.ktor.http.HttpStatusCode.Companion.NotFound

object ExpenseSubCategoryService {
    private val expenseSubCategoryRepository = ExpenseSubCategoryRepository

    suspend fun addExpenseSubCategoryToExpenseCategory(
        expenseCategoryId: Int,
        expenseSubCategory: ExpenseCategorySubmissionView
    ): ExpenseCategoryDto {
        return expenseSubCategoryRepository.create(expenseCategoryId, expenseSubCategory)
            ?: fail("Cannot find Expense Category $expenseCategoryId", NotFound)
    }

    suspend fun removeExpenseSubCategoryFromExpenseCategory(expenseSubCategoryId: Int): ExpenseCategoryDto {
        return expenseSubCategoryRepository.delete(expenseSubCategoryId)
            ?: fail("Cannot find Expense Sub-Category $expenseSubCategoryId", NotFound)
    }

    suspend fun updateExpenseSubCategory(
        expenseSubCategoryId: Int,
        expenseSubCategory: ExpenseCategorySubmissionView
    ): ExpenseCategoryDto {
        return expenseSubCategoryRepository.update(expenseSubCategoryId, expenseSubCategory)
            ?: fail("Cannot find Expense Sub-Category $expenseSubCategoryId", NotFound)
    }
}