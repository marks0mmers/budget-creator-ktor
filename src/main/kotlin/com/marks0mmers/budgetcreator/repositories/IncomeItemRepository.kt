package com.marks0mmers.budgetcreator.repositories

import com.marks0mmers.budgetcreator.models.persistent.IncomeItem
import com.marks0mmers.budgetcreator.models.persistent.IncomeItem.IncomeItems
import com.marks0mmers.budgetcreator.models.persistent.IncomeSource
import com.marks0mmers.budgetcreator.models.persistent.IncomeSource.IncomeSources
import com.marks0mmers.budgetcreator.models.views.IncomeItemSubmissionView
import com.marks0mmers.budgetcreator.util.fail
import kotlinx.datetime.toJavaLocalDate
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object IncomeItemRepository {
    suspend fun findAllByBudgetId(budgetId: Int) = newSuspendedTransaction {
        val incomeSourcesForBudget = IncomeSource.find { IncomeSources.budgetId eq budgetId }.map { it.id }
        IncomeItem.find { IncomeItems.incomeSource inList incomeSourcesForBudget }.map { it.toDto() }
    }

    suspend fun create(incomeItem: IncomeItemSubmissionView) = newSuspendedTransaction {
        val incomeSource = IncomeSource.findById(incomeItem.incomeSourceId)
            ?: fail("Cannot find income source ID: ${incomeItem.incomeSourceId}")
        IncomeItem.new {
            name = incomeItem.name
            amount = incomeItem.amount
            dateTransacted = incomeItem.dateTransacted.toJavaLocalDate()
            this.incomeSource = incomeSource
        }.toDto()
    }

    suspend fun update(incomeItemId: Int, incomeItem: IncomeItemSubmissionView) = newSuspendedTransaction {
        val incomeSource = IncomeSource.findById(incomeItem.incomeSourceId)
            ?: fail("Cannot find income source ID: ${incomeItem.incomeSourceId}")
        IncomeItem.findById(incomeItemId)?.apply {
            name = incomeItem.name
            amount = incomeItem.amount
            dateTransacted = incomeItem.dateTransacted.toJavaLocalDate()
            this.incomeSource = incomeSource
        }?.toDto()
    }

    suspend fun delete(incomeItemId: Int) = newSuspendedTransaction {
        IncomeItem.findById(incomeItemId)?.apply { delete() }?.toDto()
    }
}