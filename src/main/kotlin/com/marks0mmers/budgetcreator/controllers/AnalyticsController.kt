package com.marks0mmers.budgetcreator.controllers

import com.marks0mmers.budgetcreator.services.AnalyticsService
import com.marks0mmers.budgetcreator.util.fail
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.datetime.toLocalDate

object AnalyticsController {
    private val analyticsService = AnalyticsService

    fun Route.analyticsRoutes() {
        route("/analytics/{budgetId}") {
            authenticate {
                get("/percentComplete") {
                    val budgetId = call.parameters["budgetId"]?.toIntOrNull() ?: fail("Invalid Budget ID")
                    val dateFilter = call.request.queryParameters["date"]?.toLocalDate() ?: fail("Invalid Date")
                    val analytics = analyticsService.getPercentSourcesComplete(budgetId, dateFilter)
                    call.respond(analytics)
                }

                get("/subCategoryPercentages") {
                    val budgetId = call.parameters["budgetId"]?.toIntOrNull() ?: fail("Invalid Budget ID")
                    val analytics = analyticsService.getPercentOfSubCategoriesToTotalIncome(budgetId)
                    call.respond(analytics)
                }
            }
        }
    }
}