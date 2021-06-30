package com.marks0mmers.budgetcreator.models.persistent

import com.marks0mmers.budgetcreator.models.dto.UserDto
import com.marks0mmers.budgetcreator.models.persistent.UserRole.UserRoles
import com.marks0mmers.budgetcreator.models.types.DtoConvertible
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

class User(id: EntityID<Int>) : IntEntity(id), DtoConvertible<UserDto> {

    object Users : IntIdTable("users") {
        val username = varchar("name", 50).index(isUnique = true)
        val password = varchar("password", 300)
        val firstName = varchar("first_name", 50)
        val lastName = varchar("last_name", 50)
        val enabled = bool("enabled")
    }

    companion object : IntEntityClass<User>(Users)

    var username by Users.username
    var password by Users.password
    var firstName by Users.firstName
    var lastName by Users.lastName
    var enabled by Users.enabled
    val roles by UserRole referrersOn UserRoles.userId

    override fun toDto() = UserDto(this)
}