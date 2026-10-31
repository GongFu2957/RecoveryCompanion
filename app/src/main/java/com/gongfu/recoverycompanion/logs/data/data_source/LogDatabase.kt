package com.gongfu.recoverycompanion.logs.data.data_source

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec

// Version 2 Database
@RenameColumn.Entries(
    RenameColumn(
        tableName = "logs_entries",
        fromColumnName = "outcome",
        toColumnName = "outcomeCode"
    )
)

class RenameOutcomeToOutcomeCodeSpec : AutoMigrationSpec
@Database(
    entities = [LogEntryEntity::class],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1,
            to = 2,
            spec = RenameOutcomeToOutcomeCodeSpec::class
            )
    ]
)

abstract class LogDatabase : RoomDatabase() {
    abstract val logEntryDao: LogEntryDao

    companion object{
        const val DATABASE_NAME = "logs_db"
    }
}