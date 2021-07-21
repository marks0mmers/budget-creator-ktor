package com.marks0mmers.budgetcreator.controllers

import com.marks0mmers.budgetcreator.config.JwtConfig.generateToken
import com.marks0mmers.budgetcreator.models.dto.UserDto
import com.marks0mmers.budgetcreator.models.persistent.Role

abstract class BaseTest {
    protected val userDto = UserDto(
        id = 1,
        username = "marks0mmers",
        firstName = "Mark",
        lastName = "Sommers",
        enabled = true,
        roles = listOf(Role.ROLE_USER)
    ).apply { generateToken() }
}
