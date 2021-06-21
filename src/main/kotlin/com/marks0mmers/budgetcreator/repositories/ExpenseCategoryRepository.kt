package com.marks0mmers.budgetcreator.repositories

import com.marks0mmers.budgetcreator.models.persistent.ExpenseCategory
import com.marks0mmers.budgetcreator.models.persistent.ExpenseCategory.ExpenseCategories
import com.marks0mmers.budgetcreator.models.persistent.User
import com.marks0mmers.budgetcreator.models.views.ExpenseCategorySubmissionView
import com.marks0mmers.budgetcreator.util.fail
import kotlinx.coroutines.flow.asFlow
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

/**
 * The singleton responsible for interacting with the Postgres expense_category table
 *
 * @author Mark Sommers
 */
object ExpenseCategoryRepository {

    /**
     * Gets all of the expense categories
     *
     * @return The list of expense categories
     */
    suspend fun findAllByUser(userId: Int) = newSuspendedTransaction {
        ExpenseCategory.find { ExpenseCategories.userId eq userId }.map { it.toDto() }
    }

    /**
     * Create a new expense category
     *
     * @param ec The expense category to create
     * @return The created expense category
     */
    suspend fun create(ec: ExpenseCategorySubmissionView, userId: Int) = newSuspendedTransaction {
        val user = User.findById(userId) ?: fail("Cannot find user $userId")
        ExpenseCategory.new {
            name = ec.name
            this.user = user
        }.toDto()
    }

    /**
     * Update an expense category
     *
     * @param expenseCategoryId The ID of the expense category to update
     * @param expenseCategorySubmission The new values for the expense category
     * @return The updated expense category
     */
    suspend fun update(expenseCategoryId: Int, expenseCategorySubmission: ExpenseCategorySubmissionView) = newSuspendedTransaction {
        ExpenseCategory.findById(expenseCategoryId)?.apply {
            name = expenseCategorySubmission.name
        }?.toDto()
    }

    /**
     * Delete an expense category
     *
     * @param expenseCategoryId The ID of the expense category to delete
     * @return The deleted expense category
     */
    suspend fun delete(expenseCategoryId: Int) = newSuspendedTransaction {
        ExpenseCategory.findById(expenseCategoryId)?.apply { delete() }?.toDto()
    }
}