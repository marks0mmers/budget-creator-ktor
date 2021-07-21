package com.marks0mmers.budgetcreator.db.migration

import com.marks0mmers.budgetcreator.db.CustomMigration
import com.marks0mmers.budgetcreator.models.persistent.Budget.Budgets
import com.marks0mmers.budgetcreator.models.persistent.ExpenseCategory.ExpenseCategories
import org.flywaydb.core.api.migration.Context
import org.jetbrains.exposed.sql.transactions.transaction

@Suppress("unused")
class `3AddUserIdToCategory` : CustomMigration(3, "Add User Id To Category") {
    override fun migrate(context: Context?) {
        transaction {
            if (exec("""
                SELECT COUNT(*) as count
                FROM information_schema.columns 
                WHERE table_name = '${ExpenseCategories.tableName}' 
                AND column_name = '${ExpenseCategories.userId.name}'
            """.trimIndent()) {
                    it.next()
                    it.getInt("count")
            } == 0) {
                execInBatch(ExpenseCategories.userId.createStatement())
            }
            if (exec("""
                    SELECT COUNT(*) as count
                    FROM information_schema.columns 
                    WHERE table_name = '${Budgets.tableName}' 
                    AND column_name = 'primary_user_id'
                """.trimIndent()) {
                it.next()
                it.getInt("count")
            }!! > 0) {
                exec("ALTER TABLE ${Budgets.tableName} RENAME COLUMN primary_user_id TO ${Budgets.userId.name}")
            }
        }
    }
}