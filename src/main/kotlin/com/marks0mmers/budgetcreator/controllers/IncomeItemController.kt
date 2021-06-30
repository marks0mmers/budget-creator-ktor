package com.marks0mmers.budgetcreator.controllers

import com.marks0mmers.budgetcreator.models.views.IncomeItemSubmissionView
import com.marks0mmers.budgetcreator.services.IncomeItemService
import com.marks0mmers.budgetcreator.util.fail
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

object IncomeItemController {
    private val incomeItemService = IncomeItemService

    fun Route.incomeItemRoutes() {
        route("/incomeItems") {
            authenticate {
                get {
                    val budgetId = call.request.queryParameters["budgetId"]?.toIntOrNull()
                        ?: fail("Request missing budget Id")
                    val incomeItems = incomeItemService.getAllIncomeItemsForBudget(budgetId)
                    call.respond(incomeItems)
                }

                post {
                    val body = call.receive<IncomeItemSubmissionView>()
                    val createdIncomeItem = incomeItemService.createIncomeItem(body)
                    call.respond(createdIncomeItem)
                }

                route("/{incomeItemId}") {
                    put {
                        val body = call.receive<IncomeItemSubmissionView>()
                        val incomeItemId = call.parameters["incomeItemId"]?.toIntOrNull()
                            ?: fail("Invalid Income Item ID")
                        val updatedIncomeItem = incomeItemService.updateIncomeItem(incomeItemId, body)
                        call.respond(updatedIncomeItem)
                    }

                    delete {
                        val incomeItemId = call.parameters["incomeItemId"]?.toIntOrNull()
                            ?: fail("Invalid Income Item ID")
                        val deletedIncomeItem = incomeItemService.deleteIncomeItem(incomeItemId)
                        call.respond(deletedIncomeItem)
                    }
                }
            }
        }
    }
}