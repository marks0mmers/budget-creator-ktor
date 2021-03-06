package com.marks0mmers.budgetcreator.models.views

import kotlinx.serialization.Serializable

@Serializable
data class ExpenseSourceSubmissionView(
    val name: String,
    val amount: Double,
    val categoryId: Int,
    val subCategoryId: Int?
)