package com.marks0mmers.budgetcreator.models.dto

import com.marks0mmers.budgetcreator.models.persistent.ExpenseSubCategory
import kotlinx.serialization.Serializable

/**
 * The DTO that represents an Expense Sub-category
 *
 * @property id The id of the sub-category
 * @property name The name of the sub-category
 */
@Serializable
data class ExpenseSubCategoryDto(
    val id: Int,
    val name: String,
) {
    /**
     * Creates a DTO based on the expense sub-category entity
     */
    constructor(expenseSubCategory: ExpenseSubCategory) : this(
        expenseSubCategory.id.value,
        expenseSubCategory.name,
    )
}