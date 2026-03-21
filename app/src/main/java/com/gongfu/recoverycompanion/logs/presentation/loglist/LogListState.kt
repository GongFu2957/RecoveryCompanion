package com.gongfu.recoverycompanion.logs.presentation.loglist

import androidx.compose.runtime.Immutable
import com.gongfu.recoverycompanion.logs.domain.model.LogEntry
import com.gongfu.recoverycompanion.logs.domain.util.LogOrder
import com.gongfu.recoverycompanion.logs.domain.util.OrderType

@Immutable
data class LogListState(
    val isLoading: Boolean = false,
    val logs: List<LogEntry> = emptyList(),
    val selectedLogById: Long?  = null,
    val logOrder: LogOrder = LogOrder.Date(OrderType.Descending),
    val isFilterOpen: Boolean = false,
)
