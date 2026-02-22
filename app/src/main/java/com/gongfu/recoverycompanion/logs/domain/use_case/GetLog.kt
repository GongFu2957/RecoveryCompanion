package com.gongfu.recoverycompanion.logs.domain.use_case

import com.gongfu.recoverycompanion.logs.domain.model.LogEntry
import com.gongfu.recoverycompanion.logs.domain.repository.LogRepository

class GetLog(
    private val repository: LogRepository
) {
    suspend operator fun invoke(id: Long): LogEntry? {
        return repository.getLogById(id)
    }
}