package com.gongfu.recoverycompanion.logs.presentation.loglist

import androidx.compose.runtime.Immutable
import com.gongfu.recoverycompanion.logs.domain.model.LogEntry

@Immutable
data class LogListState(
    val isLoading: Boolean = false,
    val logs: List<LogEntry> = emptyList(),
    val selectedLogById: Long?  = null
)
