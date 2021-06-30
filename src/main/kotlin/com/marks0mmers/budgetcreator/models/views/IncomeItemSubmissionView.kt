package com.marks0mmers.budgetcreator.models.views

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class IncomeItemSubmissionView (
    val name: String,
    val amount: Double,
    val dateTransacted: LocalDate,
    val incomeSourceId: Int,
)