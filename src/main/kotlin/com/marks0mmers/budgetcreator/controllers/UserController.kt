package com.marks0mmers.budgetcreator.controllers

import com.marks0mmers.budgetcreator.config.JwtConfig.generateToken
import com.marks0mmers.budgetcreator.models.dto.UserDto
import com.marks0mmers.budgetcreator.models.views.AuthRequestView
import com.marks0mmers.budgetcreator.models.views.CreateUserView
import com.marks0mmers.budgetcreator.services.UserService
import com.marks0mmers.budgetcreator.util.fail
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.HttpStatusCode.Companion.Unauthorized
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

/**
 * The API Controller for User related functionality
 */
object UserController {
    /** The User Service for Business Logic */
    private val userService = UserService

    /**
     * The routes for the user controller
     */
    fun Route.userRoutes() {
        route("/users") {
            post {
                val body = call.receive<CreateUserView>()
                val isAdmin = call.request.queryParameters["isAdmin"]?.toBoolean() ?: false
                val user = userService.createUser(body, isAdmin)
                call.respond(user)
            }
            post("/login") {
                val body = call.receive<AuthRequestView>()
                val user = userService.getUserByCredentials(body.username, body.password)
                call.respond(user)
            }

            authenticate {
                get("/current") {
                    val user = call.principal<UserDto>() ?: fail("Unable to get user from Request", Unauthorized)
                    call.respond(user.apply { generateToken() })
                }

                get("/{userId}") {
                    val userId = call.parameters["userId"]?.toIntOrNull() ?: fail("Invalid User ID")
                    val user = userService.getUserById(userId)
                    call.respond(user)
                }
            }
        }
    }
}
