package com.marks0mmers.budgetcreator.models.views

import kotlinx.serialization.Serializable

/**
 * The JSON object that is used to Upsert Income Sources
 *
 * @property name The name of the income source
 * @property amount The amount of the income source
 */
@Serializable
data class IncomeSourceSubmissionView(
    val name: String,
    val amount: Double
)