package com.marks0mmers.budgetcreator.services

import com.marks0mmers.budgetcreator.models.dto.ExpenseCategoryDto
import com.marks0mmers.budgetcreator.models.persistent.ExpenseSubCategory
import com.marks0mmers.budgetcreator.models.views.ExpenseCategorySubmissionView
import com.marks0mmers.budgetcreator.repositories.ExpenseSubCategoryRepository
import com.marks0mmers.budgetcreator.util.BudgetCreatorException
import com.marks0mmers.budgetcreator.util.fail
import io.ktor.http.HttpStatusCode.Companion.NotFound

/**
 * The service layer responsible for handling [ExpenseSubCategory] functionality
 *
 * @author Mark Sommers
 */
object ExpenseSubCategoryService {

    /** The expense sub-category Postgres Repository */
    private val expenseSubCategoryRepository = ExpenseSubCategoryRepository

    /**
     * Add an expense sub-category to an expense category
     *
     * @param expenseCategoryId The expense category to add the sub-category to
     * @param expenseSubCategory The expense sub-category to add
     * @return The added expense sub-category
     * @throws BudgetCreatorException If the expense category cannot be found
     */
    suspend fun addExpenseSubCategoryToExpenseCategory(
        expenseCategoryId: Int,
        expenseSubCategory: ExpenseCategorySubmissionView
    ): ExpenseCategoryDto {
        return expenseSubCategoryRepository.create(expenseCategoryId, expenseSubCategory)
            ?: fail("Cannot find Expense Category $expenseCategoryId", NotFound)
    }

    /**
     * Remove an expense sub-category from an expense category
     *
     * @param expenseSubCategoryId The sub-category to remove
     * @return The removed expense sub-category
     * @throws BudgetCreatorException If the expense sub-category cannot be found
     */
    suspend fun removeExpenseSubCategoryFromExpenseCategory(expenseSubCategoryId: Int): ExpenseCategoryDto {
        return expenseSubCategoryRepository.delete(expenseSubCategoryId)
            ?: fail("Cannot find Expense Sub-Category $expenseSubCategoryId", NotFound)
    }

    /**
     * Update an expense sub-category on an expense category
     *
     * @param expenseSubCategoryId The expense sub-category to update
     * @param expenseSubCategory The values to update on the sub-category
     * @return The updated sub-category
     * @throws BudgetCreatorException If the expense sub-category cannot be found
     */
    suspend fun updateExpenseSubCategory(
        expenseSubCategoryId: Int,
        expenseSubCategory: ExpenseCategorySubmissionView
    ): ExpenseCategoryDto {
        return expenseSubCategoryRepository.update(expenseSubCategoryId, expenseSubCategory)
            ?: fail("Cannot find Expense Sub-Category $expenseSubCategoryId", NotFound)
    }
}