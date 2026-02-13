package com.gongfu.recoverycompanion.logs.domain

import java.time.LocalDate

data class LogEntry(
    val creationDate: LocalDate,
    val id: Int,
    val title: String,
    val description: String,
    val trigger: String,
    val location: String,
    val intensityLevel: Int,
    val bodyResponse: String
)
