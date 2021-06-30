package com.marks0mmers.budgetcreator.models.views

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequestView(
    val username: String,
    val password: String
)