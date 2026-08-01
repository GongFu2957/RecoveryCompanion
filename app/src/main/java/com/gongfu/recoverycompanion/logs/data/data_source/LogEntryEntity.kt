package com.gongfu.recoverycompanion.logs.data.data_source

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "logs_entries")
data class LogEntryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: Long,
    val title: String,
    val description: String,
    val trigger: String,
    val location: String,
    val intensityLevel: Int,
    val bodyResponse: String,
    val outcomeCode: Int
)

