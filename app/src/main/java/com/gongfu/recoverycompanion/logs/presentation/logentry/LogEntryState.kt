package com.gongfu.recoverycompanion.logs.presentation.logentry

import androidx.compose.runtime.Immutable
import com.gongfu.recoverycompanion.logs.domain.LogEntry

@Immutable
data class LogEntryState(
    val logs: List<LogEntry> = emptyList(),
    val selectedLog: LogEntry? = null
)