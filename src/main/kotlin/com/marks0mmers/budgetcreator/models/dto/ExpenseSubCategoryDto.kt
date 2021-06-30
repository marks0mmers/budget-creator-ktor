package com.marks0mmers.budgetcreator.models.dto

import com.marks0mmers.budgetcreator.models.persistent.ExpenseSubCategory
import kotlinx.serialization.Serializable

@Serializable
data class ExpenseSubCategoryDto(
    val id: Int,
    val name: String,
) {
    constructor(expenseSubCategory: ExpenseSubCategory) : this(
        expenseSubCategory.id.value,
        expenseSubCategory.name,
    )
}