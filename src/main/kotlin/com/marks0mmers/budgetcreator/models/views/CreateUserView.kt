package com.marks0mmers.budgetcreator.models.views

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserView(
    val username: String,
    val password: String,
    val firstName: String,
    val lastName: String
)