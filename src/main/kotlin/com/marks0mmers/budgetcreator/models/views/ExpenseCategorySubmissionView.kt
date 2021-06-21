package com.marks0mmers.budgetcreator.models.views

import kotlinx.serialization.Serializable

/**
 * The JSON submission object for an expense category
 *
 * @property name The name of the expense category
 */
@Serializable
data class ExpenseCategorySubmissionView(
    val name: String
)