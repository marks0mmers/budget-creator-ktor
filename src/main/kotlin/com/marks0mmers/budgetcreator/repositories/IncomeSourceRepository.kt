package com.marks0mmers.budgetcreator.repositories

import com.marks0mmers.budgetcreator.models.persistent.Budget
import com.marks0mmers.budgetcreator.models.persistent.IncomeSource
import com.marks0mmers.budgetcreator.models.persistent.IncomeSource.IncomeSources
import com.marks0mmers.budgetcreator.models.views.IncomeSourceSubmissionView
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object IncomeSourceRepository {
    suspend fun findByBudgetId(budgetId: Int) = newSuspendedTransaction {
        IncomeSource.find { IncomeSources.budgetId eq budgetId }.map { it.toDto() }
    }

    suspend fun create(budgetId: Int, incomeSource: IncomeSourceSubmissionView) = newSuspendedTransaction {
        Budget.findById(budgetId)?.also { budget ->
            IncomeSource.new {
                name = incomeSource.name
                amount = incomeSource.amount
                this.budget = budget
            }
        }?.toDto()
    }

    suspend fun update(incomeSourceId: Int, incomeSource: IncomeSourceSubmissionView) = newSuspendedTransaction {
        IncomeSource.findById(incomeSourceId)?.apply {
            name = incomeSource.name
            amount = incomeSource.amount
        }?.budget?.toDto()
    }

    suspend fun delete(incomeSourceId: Int) = newSuspendedTransaction {
        IncomeSource.findById(incomeSourceId)
            ?.apply { delete() }?.budget
            ?.toDto()
    }
}