package com.marks0mmers.budgetcreator.services

import com.marks0mmers.budgetcreator.config.JwtConfig.generateToken
import com.marks0mmers.budgetcreator.models.dto.UserDto
import com.marks0mmers.budgetcreator.models.views.CreateUserView
import com.marks0mmers.budgetcreator.repositories.UserRepository
import com.marks0mmers.budgetcreator.util.fail
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.http.HttpStatusCode.Companion.Unauthorized
import org.mindrot.jbcrypt.BCrypt

object UserService {
    private val userRepository = UserRepository

    suspend fun getUserById(id: Int): UserDto {
        return userRepository.findById(id)
            ?: fail("Cannot find user with ID: $id", NotFound)
    }

    suspend fun getUserByCredentials(username: String, password: String): UserDto {
        val (user, hashedPassword) = userRepository.login(username)
        val newUser = if (BCrypt.checkpw(password, hashedPassword)) user?.apply { generateToken() } else null
        return newUser ?: fail("Invalid Credentials", Unauthorized)
    }

    suspend fun createUser(user: CreateUserView, isAdmin: Boolean = false): UserDto {
        val createdUser = userRepository.createUser(
            user.copy(password = BCrypt.hashpw(user.password, BCrypt.gensalt())),
            isAdmin
        )
        return createdUser.apply { generateToken() }
    }
}
