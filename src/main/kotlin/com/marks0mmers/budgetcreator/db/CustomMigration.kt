package com.marks0mmers.budgetcreator.db

import org.flywaydb.core.api.MigrationVersion
import org.flywaydb.core.api.MigrationVersion.fromVersion
import org.flywaydb.core.api.migration.Context
import org.flywaydb.core.api.migration.JavaMigration

abstract class CustomMigration(
    private val version: Int,
    private val description: String
) : JavaMigration {
    override fun getVersion(): MigrationVersion = fromVersion(version.toString())

    override fun getDescription() = description

    override fun getChecksum(): Int? = null

    override fun isUndo() = false

    override fun canExecuteInTransaction() = true

    abstract override fun migrate(context: Context?)
}