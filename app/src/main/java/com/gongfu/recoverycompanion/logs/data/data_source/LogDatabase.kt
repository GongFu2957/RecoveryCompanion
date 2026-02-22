package com.gongfu.recoverycompanion.logs.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LogEntryEntity::class],
    version = 1
)
abstract class LogDatabase: RoomDatabase() {
    abstract val logEntryDao: LogEntryDao

    companion object {
        const val DATABASE_NAME = "logs_db"
    }
}