package com.gongfu.recoverycompanion.logs.domain.use_case

import com.gongfu.recoverycompanion.logs.domain.model.InvalidLogException
import com.gongfu.recoverycompanion.logs.domain.model.LogEntry
import com.gongfu.recoverycompanion.logs.domain.repository.LogRepository

class AddLog(
    private val repository: LogRepository
){
    @Throws(InvalidLogException::class)

    suspend operator fun invoke(log: LogEntry) {
        if (log.title.isBlank()) {
            throw InvalidLogException("The title of the log can't be empty.")
        }
        if (log.description.isBlank()) {
            throw InvalidLogException("The content of the log can't be empty.")
        }
        repository.insertLog(log)
    }
}
