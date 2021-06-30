package com.marks0mmers.budgetcreator.repositories

import com.marks0mmers.budgetcreator.models.persistent.ExpenseCategory
import com.marks0mmers.budgetcreator.models.persistent.ExpenseCategory.ExpenseCategories
import com.marks0mmers.budgetcreator.models.persistent.User
import com.marks0mmers.budgetcreator.models.views.ExpenseCategorySubmissionView
import com.marks0mmers.budgetcreator.util.fail
import kotlinx.coroutines.flow.asFlow
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object ExpenseCategoryRepository {
    suspend fun findAllByUser(userId: Int) = newSuspendedTransaction {
        ExpenseCategory.find { ExpenseCategories.userId eq userId }.map { it.toDto() }
    }

    suspend fun create(ec: ExpenseCategorySubmissionView, userId: Int) = newSuspendedTransaction {
        val user = User.findById(userId) ?: fail("Cannot find user $userId")
        ExpenseCategory.new {
            name = ec.name
            this.user = user
        }.toDto()
    }

    suspend fun update(expenseCategoryId: Int, expenseCategorySubmission: ExpenseCategorySubmissionView) = newSuspendedTransaction {
        ExpenseCategory.findById(expenseCategoryId)?.apply {
            name = expenseCategorySubmission.name
        }?.toDto()
    }

    suspend fun delete(expenseCategoryId: Int) = newSuspendedTransaction {
        ExpenseCategory.findById(expenseCategoryId)?.apply { delete() }?.toDto()
    }
}