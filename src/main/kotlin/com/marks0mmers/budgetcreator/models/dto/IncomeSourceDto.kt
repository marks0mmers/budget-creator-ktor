package com.marks0mmers.budgetcreator.models.dto

import com.marks0mmers.budgetcreator.models.persistent.IncomeSource
import kotlinx.serialization.Serializable

@Serializable
data class IncomeSourceDto(
    val id: Int,
    val name: String,
    val amount: Double,
    val budgetId: Int,
) {
    constructor(incomeSource: IncomeSource) : this(
        incomeSource.id.value,
        incomeSource.name,
        incomeSource.amount,
        incomeSource.budget.id.value
    )
}