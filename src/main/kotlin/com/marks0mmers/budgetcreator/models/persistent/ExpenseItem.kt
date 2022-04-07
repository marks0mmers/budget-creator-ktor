package com.marks0mmers.budgetcreator.models.persistent

import com.marks0mmers.budgetcreator.models.dto.ExpenseItemDto
import com.marks0mmers.budgetcreator.models.persistent.ExpenseSource.ExpenseSources
import com.marks0mmers.budgetcreator.models.types.DtoConvertible
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.date

class ExpenseItem(id: EntityID<Int>) : IntEntity(id), DtoConvertible<ExpenseItemDto> {
    object ExpenseItems : IntIdTable("expense_items") {
        val name = varchar("name", 400)
        val amount = double("amount")
        val dateTransacted = date("date_transacted")
        val expenseSource = reference("expense_source_id", ExpenseSources)
    }

    companion object : IntEntityClass<ExpenseItem>(ExpenseItems)

    var name by ExpenseItems.name
    var amount by ExpenseItems.amount
    var dateTransacted by ExpenseItems.dateTransacted
    var expenseSource by ExpenseSource referencedOn ExpenseItems.expenseSource

    override fun toDto() = ExpenseItemDto(this)
}