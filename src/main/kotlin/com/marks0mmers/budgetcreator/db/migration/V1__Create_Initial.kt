package com.marks0mmers.budgetcreator.db.migration

import com.marks0mmers.budgetcreator.models.persistent.*
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

@Suppress("unused")
class V1__Create_Initial: BaseJavaMigration() {
    override fun migrate(context: Context?) {
        transaction {
            SchemaUtils.create(
                Budget.Budgets,
                ExpenseCategory.ExpenseCategories,
                ExpenseSource.ExpenseSources,
                ExpenseSubCategory.ExpenseSubCategories,
                IncomeSource.IncomeSources,
                User.Users,
                UserRole.UserRoles,
            )
        }
    }
}