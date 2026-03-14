package com.gongfu.recoverycompanion.logs.data.repository

import com.gongfu.recoverycompanion.logs.data.data_source.LogEntryDao
import com.gongfu.recoverycompanion.logs.data.mappers.toLogEntry
import com.gongfu.recoverycompanion.logs.data.mappers.toLogEntryEntity
import com.gongfu.recoverycompanion.logs.domain.model.LogEntry
import com.gongfu.recoverycompanion.logs.domain.repository.LogRepository
import com.gongfu.recoverycompanion.logs.domain.util.LogOrder
import com.gongfu.recoverycompanion.logs.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LogRepositoryImpl(private val logDao: LogEntryDao): LogRepository {
    override fun getLogs(logOrder: LogOrder): Flow<List<LogEntry>> =
        logDao.getLogs().map { logs ->
            when (logOrder.orderType) {
                is OrderType.Ascending -> {
                    when (logOrder) {
                        is LogOrder.Date -> logs.sortedBy { it.timestamp }
                        is LogOrder.IntensityLevel -> logs.sortedBy { it.intensityLevel }
                    }
                }
                is OrderType.Descending -> {
                    when (logOrder) {
                        is LogOrder.Date -> logs.sortedByDescending { it.timestamp }
                        is LogOrder.IntensityLevel -> logs.sortedByDescending { it.intensityLevel }
                    }
                }
            }.map { logEntryEntity -> logEntryEntity.toLogEntry() }
        }

    override suspend fun getLogById(id: Long): LogEntry? =
        logDao.getLogEntryById(id)?.toLogEntry()

    override suspend fun insertLog(log: LogEntry) =
        logDao.insertLog(log.toLogEntryEntity())

    override suspend fun deleteLog(log: LogEntry) =
        logDao.deleteLog(log.toLogEntryEntity())
}