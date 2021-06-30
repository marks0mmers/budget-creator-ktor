package com.marks0mmers.budgetcreator.models.dto

import com.marks0mmers.budgetcreator.models.persistent.ExpenseCategory
import kotlinx.serialization.Serializable

@Serializable
data class ExpenseCategoryDto(
    val id: Int,
    val name: String,
    val subCategories: List<ExpenseSubCategoryDto>
) {
    constructor(expenseCategory: ExpenseCategory) : this(
        expenseCategory.id.value,
        expenseCategory.name,
        expenseCategory.subCategories.map { ExpenseSubCategoryDto(it) }
    )
}