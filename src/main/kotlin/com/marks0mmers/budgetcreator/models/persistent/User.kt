package com.marks0mmers.budgetcreator.models.persistent

import com.marks0mmers.budgetcreator.models.dto.UserDto
import com.marks0mmers.budgetcreator.models.persistent.UserRole.UserRoles
import com.marks0mmers.budgetcreator.models.types.DtoConvertible
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

/**
 * Entity that represents the User
 *
 * @author Mark Sommers
 * @constructor
 * Internal constructor used by Expose
 *
 * @param id The Primary Key for the User
 */
class User(id: EntityID<Int>) : IntEntity(id), DtoConvertible<UserDto> {

    /** The object that represents the Database table users */
    object Users : IntIdTable("users") {
        /** The username of the user */
        val username = varchar("name", 50).index(isUnique = true)
        /** The password of the user */
        val password = varchar("password", 300)
        /** The first name of the user */
        val firstName = varchar("first_name", 50)
        /** The last name of the user */
        val lastName = varchar("last_name", 50)
        /** Whether the user is enabled */
        val enabled = bool("enabled")
    }

    /** The companion object that allows for the query DSL */
    companion object : IntEntityClass<User>(Users)

    /** The username of the user */
    var username by Users.username
    /** The password of the user */
    var password by Users.password
    /** The first name of the user */
    var firstName by Users.firstName
    /** The last name of the user */
    var lastName by Users.lastName
    /** Whether the user is enabled */
    var enabled by Users.enabled
    /** The roles of the user */
    val roles by UserRole referrersOn UserRoles.userId

    /**
     * Converts this entity to a DTO
     * @return The DTO
     * @see DtoConvertible.toDto
     */
    override fun toDto() = UserDto(this)
}