package com.gongfu.recoverycompanion.logs.data.repository

import com.gongfu.recoverycompanion.logs.data.data_source.LogEntryDao
import com.gongfu.recoverycompanion.logs.data.data_source.LogEntryEntity
import com.gongfu.recoverycompanion.logs.data.mappers.toLogEntry
import com.gongfu.recoverycompanion.logs.data.mappers.toLogEntryEntity
import com.gongfu.recoverycompanion.logs.domain.model.LogEntry
import com.gongfu.recoverycompanion.logs.domain.repository.LogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.collections.map

class LogRepositoryImpl(private val logDao: LogEntryDao): LogRepository {
    override fun getLogs(): Flow<List<LogEntry>> =
        logDao.getLogs().map { entities ->
            entities.map(LogEntryEntity::toLogEntry)
        }

    override suspend fun getLogById(id: Long): LogEntry? =
        logDao.getLogEntryById(id)?.toLogEntry()

    override suspend fun insertLog(log: LogEntry) =
        logDao.insertLog(log.toLogEntryEntity())

    override suspend fun deleteLog(log: LogEntry) =
        logDao.deleteLog(log.toLogEntryEntity())
}