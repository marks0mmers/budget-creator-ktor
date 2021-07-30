package com.marks0mmers.budgetcreator.controllers

import com.marks0mmers.budgetcreator.models.dto.BudgetDto
import com.marks0mmers.budgetcreator.module
import com.marks0mmers.budgetcreator.services.BudgetService
import com.marks0mmers.budgetcreator.services.UserService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.mockk.coEvery
import io.mockk.mockkObject
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExperimentalSerializationApi
internal class BudgetControllerTests : BaseTest() {
    private val budget = BudgetDto(
        1,
        "Mark's Budget",
        1,
        listOf(),
        listOf()
    )

    @BeforeEach
    fun setupUser() {
        mockkObject(UserService)
        coEvery { UserService.getUserById(1) } returns userDto
    }

    @Test
    fun testGetBudgetsForUser(): Unit = withTestApplication(Application::module) {
        mockkObject(BudgetService)
        coEvery { BudgetService.getAllBudgetItemsForUser(budget.id) } returns listOf(budget)

        with(handleRequest(HttpMethod.Get, "/api/budgets") {
            addHeader(HttpHeaders.Authorization, "Bearer ${userDto.token}")
        }) {
            assertEquals(response.status(), HttpStatusCode.OK)
            assertNotNull(response.content)
            val body = Json.decodeFromString<List<BudgetDto>>(response.content!!)
            assertEquals(body, listOf(budget))
        }
    }
}