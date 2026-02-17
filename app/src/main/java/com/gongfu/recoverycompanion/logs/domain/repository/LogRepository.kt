package com.gongfu.recoverycompanion.logs.domain.repository

import com.gongfu.recoverycompanion.logs.domain.model.LogEntry
import kotlinx.coroutines.flow.Flow

interface LogRepository {
    fun getLogs(): Flow<List<LogEntry>>

    suspend fun getLogById(id: Long): LogEntry?

    suspend fun insertLog(log: LogEntry)

    suspend fun deleteLog(log: LogEntry)
}