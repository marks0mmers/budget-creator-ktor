package com.marks0mmers.budgetcreator.db.migration

import com.marks0mmers.budgetcreator.models.persistent.Budget
import com.marks0mmers.budgetcreator.models.persistent.ExpenseCategory
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.jetbrains.exposed.sql.transactions.transaction

class V3__Add_User_Id_To_Category : BaseJavaMigration() {
    override fun migrate(context: Context?) {
        transaction {
            execInBatch(ExpenseCategory.ExpenseCategories.userId.createStatement())
            exec("ALTER TABLE ${Budget.table.tableName} RENAME COLUMN primary_user_id TO ${Budget.Budgets.userId.name}")
        }
    }
}