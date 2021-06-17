package com.marks0mmers.budgetcreator.repositories

import com.marks0mmers.budgetcreator.models.persistent.Role
import com.marks0mmers.budgetcreator.models.persistent.User
import com.marks0mmers.budgetcreator.models.persistent.User.Users
import com.marks0mmers.budgetcreator.models.persistent.UserRole
import com.marks0mmers.budgetcreator.models.views.CreateUserView
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

/**
 * The singleton responsible for interacting with the Postgres user table
 *
 * @author Mark Sommers
 */
object UserRepository {

    /**
     * Finds a user by the user's ID
     *
     * @param id The user ID to find
     * @return The found user or null
     */
    suspend fun findById(id: Int) = newSuspendedTransaction {
        User.findById(id)?.toDto()
    }

    /**
     * Function to get the user trying to login along with their hashed password
     *
     * @param username The username to get
     * @return The user trying to login along with their password
     */
    suspend fun login(username: String) = newSuspendedTransaction {
        val user = User.find { Users.username eq username }.firstOrNull()
        user?.toDto() to user?.password
    }

    /**
     * Creates a new user
     *
     * @param createUser The user to create
     * @param isAdmin If the user is an admin user
     * @return The created user
     */
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