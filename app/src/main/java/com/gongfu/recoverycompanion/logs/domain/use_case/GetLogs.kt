package com.gongfu.recoverycompanion.logs.domain.use_case

import com.gongfu.recoverycompanion.logs.domain.model.LogEntry
import com.gongfu.recoverycompanion.logs.domain.repository.LogRepository
import com.gongfu.recoverycompanion.logs.domain.util.LogOrder
import com.gongfu.recoverycompanion.logs.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetLogs(
    private val repository: LogRepository
) {
    operator fun invoke(
        logOrder: LogOrder = LogOrder.Date(OrderType.Descending)
    ): Flow<List<LogEntry>> {
        return repository.getLogs().map { logs ->
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
            }
        }
    }
}