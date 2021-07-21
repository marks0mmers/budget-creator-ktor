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
        val incomeItems = incomeItemRepository.findAllByBudgetId(budgetId)
        val expenseItems = expenseItemRepository.findAllByBudgetId(budgetId)

        val incomePercentages = budget.incomeSources.associate { source ->
            source.name to PercentObjectView(
                percent = incomeItems
                    .filter { it.isYearMonthEqual(date) }
                    .filter { it.incomeSourceId == source.id }
                    .sumOf { it.amount } / source.amount * 100,
                color = "green"
            )
        }

        val expensePercentages = budget.expenseSources.associate { source ->
            source.name to PercentObjectView(
                percent = expenseItems
                    .filter { it.isYearMonthEqual(date) }
                    .filter { it.expenseSourceId == source.id }
                    .sumOf { it.amount } / source.amount * 100,
                color = "red"
            )
        }

        return incomePercentages + expensePercentages
    }

    suspend fun getPercentOfSubCategoriesToTotalIncome(budgetId: Int): Map<String, Double> {
        val budget = budgetRepository.findById(budgetId) ?: fail("Cannot find Budget ID: $budgetId")
        val incomeTotal = budget.incomeSources.sumOf { it.amount }

        return budget.expenseSources
            .associateBy { it.subCategory?.name ?: it.category.name }
            .mapValues { it.value.amount / incomeTotal }
    }
}