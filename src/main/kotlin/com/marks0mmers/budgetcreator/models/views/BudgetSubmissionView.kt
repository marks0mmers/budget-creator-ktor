package com.marks0mmers.budgetcreator.models.views

import kotlinx.serialization.Serializable

/**
 * The JSON submission for a budget object
 *
 * @property title The title of the budget
 */
@Serializable
data class BudgetSubmissionView(
    val title: String,
)