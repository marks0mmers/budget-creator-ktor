package com.marks0mmers.budgetcreator.models.dto

import com.marks0mmers.budgetcreator.models.persistent.Budget
import kotlinx.serialization.Serializable

@Serializable
data class BudgetDto(
    val id: Int,
    val title: String,
    val userId: Int,
    val incomeSources: List<IncomeSourceDto>,
    val expenseSources: List<ExpenseSourceDto>
) {
    constructor(budget: Budget) : this(
        budget.id.value,
        budget.title,
        budget.user.id.value,
        budget.incomeSources.map(::IncomeSourceDto),
        budget.expenseSources.map(::ExpenseSourceDto)
    )
}