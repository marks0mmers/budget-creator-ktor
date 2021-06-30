package com.marks0mmers.budgetcreator.repositories

import com.marks0mmers.budgetcreator.models.persistent.ExpenseItem
import com.marks0mmers.budgetcreator.models.persistent.ExpenseItem.ExpenseItems
import com.marks0mmers.budgetcreator.models.persistent.ExpenseSource
import com.marks0mmers.budgetcreator.models.persistent.ExpenseSource.ExpenseSources
import com.marks0mmers.budgetcreator.models.views.ExpenseItemSubmissionView
import com.marks0mmers.budgetcreator.util.fail
import kotlinx.datetime.toJavaLocalDate
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object ExpenseItemRepository {
    suspend fun findAllByBudgetId(budgetId: Int) = newSuspendedTransaction {
        val expenseSourcesForBudget = ExpenseSource.find { ExpenseSources.budgetId eq budgetId }.map { it.id }
        ExpenseItem.find { ExpenseItems.expenseSource inList expenseSourcesForBudget }.map { it.toDto() }
    }

    suspend fun create(expenseItem: ExpenseItemSubmissionView) = newSuspendedTransaction {
        val expenseSource = ExpenseSource.findById(expenseItem.expenseSourceId)
            ?: fail("Cannot find expense source ID: ${expenseItem.expenseSourceId}")
        ExpenseItem.new {
            name = expenseItem.name
            amount = expenseItem.amount
            dateTransacted = expenseItem.dateTransacted.toJavaLocalDate()
            this.expenseSource = expenseSource
        }.toDto()
    }

    suspend fun update(expenseItemId: Int, expenseItem: ExpenseItemSubmissionView) = newSuspendedTransaction {
        val expenseSource = ExpenseSource.findById(expenseItem.expenseSourceId)
            ?: fail("Cannot find expense source ID: ${expenseItem.expenseSourceId}")
        ExpenseItem.findById(expenseItemId)?.apply {
            name = expenseItem.name
            amount = expenseItem.amount
            dateTransacted = expenseItem.dateTransacted.toJavaLocalDate()
            this.expenseSource = expenseSource
        }?.toDto()
    }

    suspend fun delete(expenseItemId: Int) = newSuspendedTransaction {
        ExpenseItem.findById(expenseItemId)?.apply { delete() }?.toDto()
    }
}