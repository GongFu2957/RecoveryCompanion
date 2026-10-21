package com.gongfu.recoverycompanion.logs.data.data_source

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

// Version 2 Database
@Database(
    entities = [LogEntryEntity::class],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)
abstract class LogDatabase : RoomDatabase() {
    abstract val logEntryDao: LogEntryDao

    companion object{
        const val DATABASE_NAME = "logs_db"
    }
}