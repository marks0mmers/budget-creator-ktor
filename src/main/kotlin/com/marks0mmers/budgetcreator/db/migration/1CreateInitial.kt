package com.marks0mmers.budgetcreator.db.migration

import com.marks0mmers.budgetcreator.db.CustomMigration
import com.marks0mmers.budgetcreator.models.persistent.*
import org.flywaydb.core.api.migration.Context
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

@Suppress("unused")
class `1CreateInitial` : CustomMigration(1, "Create Initial") {

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

    override fun isBaselineMigration() = true
}