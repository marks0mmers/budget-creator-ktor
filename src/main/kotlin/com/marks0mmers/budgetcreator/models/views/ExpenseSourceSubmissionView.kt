package com.marks0mmers.budgetcreator.models.views

import kotlinx.serialization.Serializable

/**
 * The JSON submission object for an Expense Source
 *
 * @property name The name of the expense source
 * @property amount The amount of the expense source
 * @property categoryId The category of the expense source
 * @property subCategoryId The sub-category of the expense source
 */
@Serializable
data class ExpenseSourceSubmissionView(
    val name: String,
    val amount: Double,
    val categoryId: Int,
    val subCategoryId: Int?
)