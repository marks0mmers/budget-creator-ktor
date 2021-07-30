package com.marks0mmers.budgetcreator.db

import com.viartemev.ktor.flyway.FlywayFeature
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.*
import org.jetbrains.exposed.sql.Database
import java.net.URI

@Suppress("EXPERIMENTAL_API_USAGE")
object DbConfig {
    fun Application.dbConfig() {
        val uri = environment.config.propertyOrNull("postgresql.connectionStringProd")?.getString()?.let(::URI)
        val username = uri?.userInfo?.split(":")?.get(0)
        val password = uri?.userInfo?.split(":")?.get(1)
        val dbUrl = uri?.run { "jdbc:postgresql://$host:$port$path" }
        val db = HikariDataSource(HikariConfig().apply {
            driverClassName = "org.postgresql.Driver"
            jdbcUrl = dbUrl ?: "${environment.config.propertyOrNull("postgresql.connectionString")?.getString()}"
            this.username = username
            this.password = password
            maximumPoolSize = environment.config.propertyOrNull("postgresql.threadPoolSize")?.getString()?.toIntOrNull() ?: 3
            isAutoCommit = false
            validate()
        })
        Database.connect(db)
        install(FlywayFeature) {
            dataSource = db
            locations = arrayOf("com/marks0mmers/budgetcreator/db")
        }
    }
}