package com.marks0mmers.budgetcreator.repositories

import com.marks0mmers.budgetcreator.models.persistent.Budget
import com.marks0mmers.budgetcreator.models.persistent.Budget.Budgets
import com.marks0mmers.budgetcreator.models.persistent.User
import com.marks0mmers.budgetcreator.models.views.BudgetSubmissionView
import com.marks0mmers.budgetcreator.util.fail
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object BudgetRepository {
    suspend fun findAllByUserId(userId: Int) = newSuspendedTransaction {
        Budget.find { Budgets.userId eq userId }.map { it.toDto() }
    }

    suspend fun findById(budgetId: Int) = newSuspendedTransaction {
        Budget.findById(budgetId)?.toDto()
    }

    suspend fun findByTitle(title: String) = newSuspendedTransaction {
        Budget.find { Budgets.title eq title }.map { it.toDto() }
    }

    suspend fun create(budget: BudgetSubmissionView, userId: Int) = newSuspendedTransaction {
        val user = User.findById(userId) ?: fail("Cannot find user $userId")
        Budget.new {
            title = budget.title
            this.user = user
        }.toDto()
    }

    suspend fun update(budgetId: Int, budget: BudgetSubmissionView) = newSuspendedTransaction {
        Budget.findById(budgetId)?.apply {
            title = budget.title
        }?.toDto()
    }

    suspend fun delete(budgetId: Int) = newSuspendedTransaction {
        Budget.findById(budgetId)?.apply { delete() }?.toDto()
    }
}