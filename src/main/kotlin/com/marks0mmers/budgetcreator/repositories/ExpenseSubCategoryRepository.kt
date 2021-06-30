package com.marks0mmers.budgetcreator.repositories

import com.marks0mmers.budgetcreator.models.persistent.ExpenseCategory
import com.marks0mmers.budgetcreator.models.persistent.ExpenseSubCategory
import com.marks0mmers.budgetcreator.models.views.ExpenseCategorySubmissionView
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object ExpenseSubCategoryRepository {
    suspend fun create(expenseCategoryId: Int, expenseSubCategory: ExpenseCategorySubmissionView) = newSuspendedTransaction {
        ExpenseCategory.findById(expenseCategoryId)?.also { expenseCategory ->
            ExpenseSubCategory.new {
                this.expenseCategory = expenseCategory
                name = expenseSubCategory.name
            }
        }?.toDto()
    }

    suspend fun update(expenseSubCategoryId: Int, expenseSubCategory: ExpenseCategorySubmissionView) = newSuspendedTransaction {
        ExpenseSubCategory.findById(expenseSubCategoryId)?.apply {
            name = expenseSubCategory.name
        }?.expenseCategory?.toDto()
    }

    suspend fun delete(expenseSubCategoryId: Int) = newSuspendedTransaction {
        ExpenseSubCategory.findById(expenseSubCategoryId)?.apply { delete() }?.expenseCategory?.toDto()
    }
}