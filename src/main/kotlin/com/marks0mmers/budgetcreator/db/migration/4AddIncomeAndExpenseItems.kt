package com.marks0mmers.budgetcreator.db.migration

import com.marks0mmers.budgetcreator.db.CustomMigration
import com.marks0mmers.budgetcreator.models.persistent.ExpenseItem
import com.marks0mmers.budgetcreator.models.persistent.IncomeItem
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

@Suppress("unused")
class `4AddIncomeAndExpenseItems` : CustomMigration(4, "Add Income and Expense Items") {
    override fun migrate(context: Context?) {
        transaction {
            SchemaUtils.create(
                IncomeItem.IncomeItems,
                ExpenseItem.ExpenseItems,
            )
        }
    }
}