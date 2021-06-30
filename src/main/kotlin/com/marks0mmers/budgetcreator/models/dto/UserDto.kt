package com.marks0mmers.budgetcreator.models.dto

import com.marks0mmers.budgetcreator.models.persistent.Role
import com.marks0mmers.budgetcreator.models.persistent.User
import io.ktor.auth.*
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Int,
    val username: String,
    val firstName: String,
    val lastName: String,
    var enabled: Boolean,
    var roles: List<Role>,
    var token: String? = null
) : Principal {
    constructor(user: User) : this(
        user.id.value,
        user.username,
        user.firstName,
        user.lastName,
        user.enabled,
        user.roles.map { it.role }
    )
}
