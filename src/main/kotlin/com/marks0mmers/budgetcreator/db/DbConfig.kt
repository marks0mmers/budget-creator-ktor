package com.marks0mmers.budgetcreator.db

import com.viartemev.ktor.flyway.FlywayFeature
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.*
import org.jetbrains.exposed.sql.Database

@Suppress("EXPERIMENTAL_API_USAGE")
object DbConfig {
    fun Application.dbConfig() {
        val db = HikariDataSource(HikariConfig().apply {
            driverClassName = "org.postgresql.Driver"
            jdbcUrl = "jdbc:${environment.config.propertyOrNull("postgresql.connectionString")?.getString()}"
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