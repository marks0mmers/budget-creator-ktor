package com.marks0mmers.budgetcreator.db.migration

import com.marks0mmers.budgetcreator.db.CustomMigration
import com.marks0mmers.budgetcreator.models.persistent.ExpenseCategory
import com.marks0mmers.budgetcreator.models.persistent.ExpenseSubCategory
import org.flywaydb.core.api.migration.Context
import org.jetbrains.exposed.sql.transactions.transaction

@Suppress("unused")
class `2RemoveCategoryDescription` : CustomMigration(2, "Remove Category Description") {
    override fun migrate(context: Context?) {
        transaction {
            exec("ALTER TABLE ${ExpenseCategory.table.tableName} DROP COLUMN IF EXISTS \"description\"")
            exec("ALTER TABLE ${ExpenseSubCategory.table.tableName} DROP COLUMN IF EXISTS \"description\"")
        }
    }
}