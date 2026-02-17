package com.gongfu.recoverycompanion.logs.domain

import androidx.room.Entity

@Entity
data class LogEntry(
    val timeStamp: Long,
    val id: Int,
    val title: String,
    val description: String,
    val trigger: String,
    val location: String,
    val intensityLevel: Int,
    val bodyResponse: String
)
