package com.marks0mmers.budgetcreator.models.views

import kotlinx.serialization.Serializable

@Serializable
data class IncomeSourceSubmissionView(
    val name: String,
    val amount: Double
)