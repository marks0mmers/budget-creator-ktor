package com.marks0mmers.budgetcreator.services

import com.marks0mmers.budgetcreator.models.dto.BudgetDto
import com.marks0mmers.budgetcreator.models.persistent.Budget
import com.marks0mmers.budgetcreator.models.views.BudgetSubmissionView
import com.marks0mmers.budgetcreator.repositories.BudgetRepository
import com.marks0mmers.budgetcreator.util.fail
import com.marks0mmers.budgetcreator.util.BudgetCreatorException

/**
 * The Service layer responsible for handling [Budget] functionality
 *
 * @author Mark Sommers
 */
object BudgetService {

    /** The budget Postgres Repository */
    private val budgetRepository = BudgetRepository

    /**
     * Get the budgets for the current user
     *
     * @param userId The username to get budgets for
     * @return The list of budgets for the user
     */
    suspend fun getAllBudgetItemsForUser(userId: Int): List<BudgetDto> {
        return budgetRepository
            .findAllByUsername(userId)
    }

    /**
     * Create a new budget for a user
     *
     * @param budget The budget to create
     * @param userId The username to create the budget for
     * @return The created budget
     */
    suspend fun createBudgetForUser(budget: BudgetSubmissionView, userId: Int): BudgetDto {
        return budgetRepository
            .create(budget, userId)
    }

    /**
     * Update a budget's information
     *
     * @param budgetId The budget to update
     * @param budgetSubmission The new properties to set on the budget
     * @return The updated budget
     * @throws BudgetCreatorException If the budget cannot be found
     */
    suspend fun updateBudget(budgetId: Int, budgetSubmission: BudgetSubmissionView): BudgetDto {
        return budgetRepository
            .update(budgetId, budgetSubmission)
            ?: fail("Failed to find Budget $budgetId")
    }

    /**
     * Deletes a budget
     *
     * @param budgetId The ID of the budget to delete
     * @return The deleted budget
     * @throws BudgetCreatorException If the budget cannot be found
     */
    suspend fun deleteBudget(budgetId: Int): BudgetDto {
        return budgetRepository.delete(budgetId)
            ?: fail("Failed to find Budget $budgetId")
    }
}