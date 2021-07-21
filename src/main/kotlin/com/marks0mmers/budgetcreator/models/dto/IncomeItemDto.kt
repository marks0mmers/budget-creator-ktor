package com.marks0mmers.budgetcreator.models.dto

import com.marks0mmers.budgetcreator.models.persistent.IncomeItem
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.serialization.Serializable

@Serializable
data class IncomeItemDto(
    val id: Int,
    val name: String,
    val amount: Double,
    val dateTransacted: LocalDate,
    val incomeSourceId: Int,
    val incomeSourceName: String
) {
    constructor(incomeItem: IncomeItem) : this(
        incomeItem.id.value,
        incomeItem.name,
        incomeItem.amount,
        incomeItem.dateTransacted.toKotlinLocalDate(),
        incomeItem.incomeSource.id.value,
        incomeItem.incomeSource.name
    )

    fun isYearMonthEqual(date: LocalDate) = dateTransacted.year == date.year && dateTransacted.month == date.month
}