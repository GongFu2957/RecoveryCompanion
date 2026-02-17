package com.gongfu.recoverycompanion.logs.domain.model

import java.time.ZonedDateTime

data class LogEntry(
    val id: Long,
    val dateTime: ZonedDateTime,
    val title: String,
    val description: String,
    val trigger: String,
    val location: String,
    val intensityLevel: Int,
    val bodyResponse: String
)