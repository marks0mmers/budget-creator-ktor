package com.marks0mmers.budgetcreator.repositories

import com.marks0mmers.budgetcreator.models.persistent.Role
import com.marks0mmers.budgetcreator.models.persistent.User
import com.marks0mmers.budgetcreator.models.persistent.User.Users
import com.marks0mmers.budgetcreator.models.persistent.UserRole
import com.marks0mmers.budgetcreator.models.views.CreateUserView
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object UserRepository {
    suspend fun findById(id: Int) = newSuspendedTransaction {
        User.findById(id)?.toDto()
    }

    suspend fun login(username: String) = newSuspendedTransaction {
        val user = User.find { Users.username eq username }.firstOrNull()
        user?.toDto() to user?.password
    }

    suspend fun createUser(createUser: CreateUserView, isAdmin: Boolean = false) = newSuspendedTransaction {
        User.new {
            username = createUser.username
            password = createUser.password
            firstName = createUser.firstName
            lastName = createUser.lastName
            enabled = true
        }.also { saved ->
            UserRole.new {
                user = saved
                role = Role.ROLE_USER
            }
            if (isAdmin) {
                UserRole.new {
                    user = saved
                    role = Role.ROLE_ADMIN
                }
            }
        }.toDto()

    }
}