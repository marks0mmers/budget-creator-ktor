package com.marks0mmers.budgetcreator.models.persistent

import com.marks0mmers.budgetcreator.models.dto.IncomeItemDto
import com.marks0mmers.budgetcreator.models.persistent.IncomeSource.IncomeSources
import com.marks0mmers.budgetcreator.models.types.DtoConvertible
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.date

class IncomeItem(id: EntityID<Int>) : IntEntity(id), DtoConvertible<IncomeItemDto> {
    object IncomeItems : IntIdTable("income_items") {
        val name = varchar("name", 400)
        val amount = double("amount")
        val dateTransacted = date("date_transacted")
        val incomeSource = reference("income_source_id", IncomeSources)
    }

    companion object : IntEntityClass<IncomeItem>(IncomeItems)

    var name by IncomeItems.name
    var amount by IncomeItems.amount
    var dateTransacted by IncomeItems.dateTransacted
    var incomeSource by IncomeSource referencedOn IncomeItems.incomeSource

    override fun toDto() = IncomeItemDto(this)
}