package com.marks0mmers.budgetcreator.repositories

import com.marks0mmers.budgetcreator.models.persistent.Budget
import com.marks0mmers.budgetcreator.models.persistent.Budget.Budgets
import com.marks0mmers.budgetcreator.models.persistent.User
import com.marks0mmers.budgetcreator.models.views.BudgetSubmissionView
import com.marks0mmers.budgetcreator.util.fail
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

/**
 * The singleton responsible for interacting with the Postgres budget table
 *
 * @author Mark Sommers
 */
object BudgetRepository {

    /**
     * Find all budgets for a user
     *
     * @param userId The user to get budgets for
     * @return The list of budgets
     */
    suspend fun findAllByUsername(userId: Int) = newSuspendedTransaction {
        Budget.find { Budgets.userId eq userId }.map { it.toDto() }
    }

    suspend fun findById(budgetId: Int) = newSuspendedTransaction {
        Budget.findById(budgetId)?.toDto()
    }

    /**
     * Create a budget
     *
     * @param budget The budget values to create
     * @param userId The user to add the budget to
     * @return The created budget
     */
    suspend fun create(budget: BudgetSubmissionView, userId: Int) = newSuspendedTransaction {
        val user = User.findById(userId) ?: fail("Cannot find user $userId")
        Budget.new {
            title = budget.title
            this.user = user
        }.toDto()
    }

    /**
     * Updates a budget
     *
     * @param budgetId The id of the budget to update
     * @param budget The new values to update the budget with
     * @return The updated budget
     */
    suspend fun update(budgetId: Int, budget: BudgetSubmissionView) = newSuspendedTransaction {
        Budget.findById(budgetId)?.apply {
            title = budget.title
        }?.toDto()
    }

    /**
     * Delete a budget
     *
     * @param budgetId The id of the budget to delete
     * @return The deleted budget
     */
    suspend fun delete(budgetId: Int) = newSuspendedTransaction {
        Budget.findById(budgetId)?.apply { delete() }?.toDto()
    }
}