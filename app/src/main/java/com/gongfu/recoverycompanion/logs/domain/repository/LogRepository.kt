package com.gongfu.recoverycompanion.logs.domain.repository

import com.gongfu.recoverycompanion.logs.domain.model.LogEntry
import com.gongfu.recoverycompanion.logs.domain.util.LogOrder
import com.gongfu.recoverycompanion.logs.domain.util.OrderType
import kotlinx.coroutines.flow.Flow

interface LogRepository {
    fun getLogs(logOrder: LogOrder = LogOrder.Date(OrderType.Descending)): Flow<List<LogEntry>>
    suspend fun getLogById(id: Long): LogEntry?

    suspend fun insertLog(log: LogEntry)

    suspend fun deleteLog(log: LogEntry)
}