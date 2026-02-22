package com.gongfu.recoverycompanion.logs.domain.model

data class LogEntry(
    val id: Long,
    val timestamp: Long,
    val title: String,
    val description: String,
    val trigger: String,
    val location: String,
    val intensityLevel: Int,
    val bodyResponse: String
)

class InvalidLogException(message: String) : Exception(message)