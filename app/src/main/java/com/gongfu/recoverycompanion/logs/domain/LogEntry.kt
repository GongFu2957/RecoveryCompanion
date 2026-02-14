package com.gongfu.recoverycompanion.logs.domain

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
