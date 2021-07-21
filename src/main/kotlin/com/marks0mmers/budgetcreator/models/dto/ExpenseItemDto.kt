package com.marks0mmers.budgetcreator.models.dto

import com.marks0mmers.budgetcreator.models.persistent.ExpenseItem
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.serialization.Serializable

@Serializable
data class ExpenseItemDto(
    val id: Int,
    val name: String,
    val amount: Double,
    val dateTransacted: LocalDate,
    val expenseSourceId: Int,
    val expenseSourceName: String,
    val expenseCategoryName: String,
    val expenseSubCategoryName: String?
) {
    constructor(expenseItem: ExpenseItem) : this(
        expenseItem.id.value,
        expenseItem.name,
        expenseItem.amount,
        expenseItem.dateTransacted.toKotlinLocalDate(),
        expenseItem.expenseSource.id.value,
        expenseItem.expenseSource.name,
        expenseItem.expenseSource.category.name,
        expenseItem.expenseSource.subCategory?.name
    )

    fun isYearMonthEqual(date: LocalDate) = dateTransacted.year == date.year && dateTransacted.month == date.month
}