package com.marks0mmers.budgetcreator.controllers

import com.marks0mmers.budgetcreator.models.views.IncomeSourceSubmissionView
import com.marks0mmers.budgetcreator.services.IncomeSourceService
import com.marks0mmers.budgetcreator.util.fail
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

/**
 * The API Controller for Income Source functionality
 */
object IncomeSourceController {
    /** The Income Source service for business logic functionality */
    private val incomeSourceService = IncomeSourceService

    /**
     * The API Routes for the Income Source Controller
     */
    fun Route.incomeSourceRoutes() {
        route("/incomeSource") {
            post {
                val body = call.receive<IncomeSourceSubmissionView>()
                val budgetId = call.parameters["budgetId"]?.toIntOrNull() ?: fail("Invalid Budget ID")
                val addedIncomeSource = incomeSourceService.addIncomeSourceToBudget(budgetId, body)
                call.respond(addedIncomeSource)
            }

            route("/{incomeSourceId}") {
                put {
                    val body = call.receive<IncomeSourceSubmissionView>()
                    val incomeSourceId = call.parameters["incomeSourceId"]?.toIntOrNull()
                        ?: fail("Invalid Income Source ID")
                    val updatedIncomeSource = incomeSourceService.updateIncomeSource(incomeSourceId, body)
                    call.respond(updatedIncomeSource)
                }

                delete {
                    val incomeSourceId = call.parameters["incomeSourceId"]?.toIntOrNull()
                        ?: fail("Invalid Income Source ID")
                    val removedIncomeSource = incomeSourceService.removeIncomeSourceFromBudget(incomeSourceId)
                    call.respond(removedIncomeSource)
                }
            }
        }
    }
}