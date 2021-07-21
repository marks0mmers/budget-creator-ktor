package com.marks0mmers.budgetcreator

import com.marks0mmers.budgetcreator.config.ErrorConfig.errorConfig
import com.marks0mmers.budgetcreator.config.JsonConfig.jsonConfig
import com.marks0mmers.budgetcreator.config.JwtConfig.authConfig
import com.marks0mmers.budgetcreator.controllers.AnalyticsController.analyticsRoutes
import com.marks0mmers.budgetcreator.controllers.BudgetController.budgetRoutes
import com.marks0mmers.budgetcreator.controllers.ExpenseCategoryController.expenseCategoryRoutes
import com.marks0mmers.budgetcreator.controllers.ExpenseItemController.expenseItemRoutes
import com.marks0mmers.budgetcreator.controllers.IncomeItemController.incomeItemRoutes
import com.marks0mmers.budgetcreator.controllers.UserController.userRoutes
import com.marks0mmers.budgetcreator.db.DbConfig.dbConfig
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.routing.*
import org.slf4j.event.Level

@Suppress("unused")
fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging) {
        level = Level.INFO
    }

    authConfig()
    jsonConfig()
    errorConfig()

    if (environment.config.propertyOrNull("postgresql") != null) {
        dbConfig()
    }

    routing {
        route("/api") {
            analyticsRoutes()
            budgetRoutes()
            incomeItemRoutes()
            expenseItemRoutes()
            expenseCategoryRoutes()
            userRoutes()
        }
    }
}