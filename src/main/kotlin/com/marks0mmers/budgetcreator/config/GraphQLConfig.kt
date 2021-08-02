package com.marks0mmers.budgetcreator.config

import com.apurebase.kgraphql.GraphQL
import com.marks0mmers.budgetcreator.graphql.BudgetSchema.budgetSchema
import com.marks0mmers.budgetcreator.graphql.ExpenseCategorySchema.expenseCategorySchema
import com.marks0mmers.budgetcreator.graphql.ExpenseItemSchema.expenseItemSchema
import com.marks0mmers.budgetcreator.graphql.ExpenseSourceSchema.expenseSourceSchema
import com.marks0mmers.budgetcreator.graphql.ExpenseSubCategorySchema.expenseSubCategorySchema
import com.marks0mmers.budgetcreator.graphql.IncomeItemSchema.incomeItemSchema
import com.marks0mmers.budgetcreator.graphql.IncomeSourceSchema.incomeSourceSchema
import io.ktor.application.*
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate

object GraphQLConfig {
    fun Application.graphQlConfig() {
        install(GraphQL) {
            playground = true
            schema {
                budgetSchema()
                incomeSourceSchema()
                incomeItemSchema()
                expenseSourceSchema()
                expenseItemSchema()
                expenseCategorySchema()
                expenseSubCategorySchema()

                stringScalar<LocalDate> {
                    description = "The conversion between string and Kotlin LocalDate"
                    deserialize = { date: String -> date.toLocalDate() }
                    serialize = LocalDate::toString
                }
            }
        }
    }
}