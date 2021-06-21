package com.marks0mmers.budgetcreator.db.migration

import com.marks0mmers.budgetcreator.models.persistent.ExpenseCategory
import com.marks0mmers.budgetcreator.models.persistent.ExpenseSubCategory
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.jetbrains.exposed.sql.transactions.transaction

@Suppress("unused")
class V2__Remove_Category_Description : BaseJavaMigration() {
    override fun migrate(context: Context?) {
        transaction {
            exec("ALTER TABLE ${ExpenseCategory.table.tableName} DROP COLUMN \"description\"")
            exec("ALTER TABLE ${ExpenseSubCategory.table.tableName} DROP COLUMN \"description\"")
        }
    }
}