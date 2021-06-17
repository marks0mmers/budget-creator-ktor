package com.marks0mmers.budgetcreator.controllers

import com.marks0mmers.budgetcreator.models.dto.UserDto
import com.marks0mmers.budgetcreator.models.persistent.Role
import com.marks0mmers.budgetcreator.services.UserService
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

internal class UserControllerTests {
    private val userService = Mockito.mock(UserService::class.java)

    private val userDto = UserDto(
        id = 1,
        username = "marks0mmers",
        firstName = "Mark",
        lastName = "Sommers",
        enabled = true,
        roles = listOf(Role.ROLE_USER)
    )

    @Test
    fun testLoggingIn(): Unit = withTestApplication {
        runBlocking {
            `when`(userService.getUserByCredentials(userDto.username, "Truckin09")).thenReturn(userDto)
        }
        val call = handleRequest(HttpMethod.Post, "/api/users/login") {
            setBody(Json.encodeToString(UserDto.serializer(), userDto))
        }
        assertEquals(call.response.status(), HttpStatusCode.OK)
        assertNotNull(call.response.content)
        val body = Json.decodeFromString(UserDto.serializer(), call.response.content!!)
        assertEquals(body, userDto)
    }
}