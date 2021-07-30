package com.marks0mmers.budgetcreator.services

import com.marks0mmers.budgetcreator.models.views.PercentObjectView
import com.marks0mmers.budgetcreator.repositories.*
import com.marks0mmers.budgetcreator.util.fail
import kotlinx.datetime.LocalDate

object AnalyticsService {
    private val budgetRepository = BudgetRepository
    private val incomeItemRepository = IncomeItemRepository
    private val expenseItemRepository = ExpenseItemRepository

    suspend fun getPercentSourcesComplete(budgetId: Int, date: LocalDate): Map<String, PercentObjectView> {
        val budget = budgetRepository.findById(budgetId) ?: fail("Cannot find Budget ID: $budgetId")
        val incomeItemTotalsBySource = incomeItemRepository.findAllByBudgetId(budgetId)
            .filter { it.isYearMonthEqual(date) }
            .groupBy(
                keySelector = { it.incomeSourceId },
                valueTransform = { it.amount }
            )
            .mapValues { it.value.sum() }
        val expenseItemTotalsBySource = expenseItemRepository.findAllByBudgetId(budgetId)
            .filter { it.isYearMonthEqual(date) }
            .groupBy(
                keySelector = { it.expenseSourceId },
                valueTransform = { it.amount }
            )
            .mapValues { it.value.sum() }

        val incomePercentages = budget.incomeSources.associate { source ->
            source.name to PercentObjectView(
                percent = incomeItemTotalsBySource[source.id]!! / source.amount * 100,
                color = "green"
            )
        }

        val expensePercentages = budget.expenseSources.associate { source ->
            source.name to PercentObjectView(
                percent = expenseItemTotalsBySource[source.id]!! / source.amount * 100,
                color = "red"
            )
        }

        return incomePercentages + expensePercentages
    }

    suspend fun getPercentOfSubCategoriesToTotalIncome(budgetId: Int): Map<String, Double> {
        val budget = budgetRepository.findById(budgetId) ?: fail("Cannot find Budget ID: $budgetId")
        val incomeTotal = budget.incomeSources.sumOf { it.amount }

        return budget.expenseSources
            .associate { (it.subCategory?.name ?: it.category.name) to it.amount / incomeTotal }
    }
}