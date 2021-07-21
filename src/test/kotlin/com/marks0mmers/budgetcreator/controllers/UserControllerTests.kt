package com.marks0mmers.budgetcreator.controllers

import com.marks0mmers.budgetcreator.models.dto.UserDto
import com.marks0mmers.budgetcreator.models.persistent.Role
import com.marks0mmers.budgetcreator.models.views.AuthRequestView
import com.marks0mmers.budgetcreator.models.views.CreateUserView
import com.marks0mmers.budgetcreator.module
import com.marks0mmers.budgetcreator.services.UserService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.mockk.coEvery
import io.mockk.mockkObject
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.TestInstance
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExperimentalSerializationApi
internal class UserControllerTests : BaseTest() {

    @Test
    fun testCreateUser(): Unit = withTestApplication(Application::module) {
        val adminUser = userDto.copy(roles = userDto.roles + Role.ROLE_ADMIN)
        mockkObject(UserService)
        coEvery { UserService.createUser(any(), false) } returns userDto
        coEvery { UserService.createUser(any(), true) } returns adminUser

        with(handleRequest(HttpMethod.Post, "/api/users") {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(Json.encodeToString(CreateUserView(userDto.username, "Truckin09", userDto.firstName, userDto.lastName)))
        }) {
            assertEquals(response.status(), HttpStatusCode.OK)
            assertNotNull(response.content)
            val body = Json.decodeFromString<UserDto>(response.content!!)
            assertEquals(body, userDto)
        }

        with(handleRequest(HttpMethod.Post, "/api/users?isAdmin=true") {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(Json.encodeToString(CreateUserView(userDto.username, "Truckin09", userDto.firstName, userDto.lastName)))
        }) {
            assertEquals(response.status(), HttpStatusCode.OK)
            assertNotNull(response.content)
            val body = Json.decodeFromString<UserDto>(response.content!!)
            assertEquals(body, adminUser)
        }
    }

    @Test
    fun testLoggingIn(): Unit = withTestApplication(Application::module) {
        mockkObject(UserService)
        coEvery { UserService.getUserByCredentials(userDto.username, "Truckin09") } returns userDto

        with(handleRequest(HttpMethod.Post, "/api/users/login") {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(Json.encodeToString(AuthRequestView(userDto.username, "Truckin09")))
        }) {
            assertEquals(response.status(), HttpStatusCode.OK)
            assertNotNull(response.content)
            val body = Json.decodeFromString<UserDto>(response.content!!)
            assertEquals(body, userDto)
        }
    }

    @Test
    fun testGettingCurrentUser(): Unit = withTestApplication(Application::module) {
        mockkObject(UserService)
        coEvery { UserService.getUserById(1) } returns userDto

        with(handleRequest(HttpMethod.Get, "/api/users/current") {
            addHeader(HttpHeaders.Authorization, "Bearer ${userDto.token}")
        }) {
            assertEquals(response.status(), HttpStatusCode.OK)
            assertNotNull(response.content)
            val body = Json.decodeFromString<UserDto>(response.content!!)
            assertEquals(body, userDto)
            assertEquals(body.token, userDto.token)
        }
    }

    @Test
    fun testGettingUserById(): Unit = withTestApplication(Application::module) {
        mockkObject(UserService)
        coEvery { UserService.getUserById(1) } returns userDto

        with(handleRequest(HttpMethod.Get, "/api/users/1") {
            addHeader(HttpHeaders.Authorization, "Bearer ${userDto.token}")
        }) {
            assertEquals(response.status(), HttpStatusCode.OK)
            assertNotNull(response.content)
            val body = Json.decodeFromString<UserDto>(response.content!!)
            assertEquals(body, userDto)
        }
    }
}