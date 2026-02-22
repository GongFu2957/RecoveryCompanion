package com.gongfu.recoverycompanion.logs.domain.use_case

import com.gongfu.recoverycompanion.logs.domain.model.LogEntry
import com.gongfu.recoverycompanion.logs.domain.repository.LogRepository

class DeleteLog(
    private val repository: LogRepository
) {

    suspend operator fun invoke(log: LogEntry) {
        repository.deleteLog(log)
    }
}