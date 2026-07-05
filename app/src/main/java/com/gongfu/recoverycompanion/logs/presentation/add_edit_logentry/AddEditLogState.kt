package com.gongfu.recoverycompanion.logs.presentation.add_edit_logentry

import com.gongfu.recoverycompanion.logs.domain.model.LogEntry
import com.gongfu.recoverycompanion.logs.domain.model.OutcomeType


enum class LogField { TITLE, DESCRIPTION, TRIGGER, LOCATION, BODY_RESPONSE }
data class AddEditLogState(
    val selectedLog: LogEntry? = null,
    val logId: Long? = null,
    val fieldErrors: Map<LogField, String> = emptyMap(),
    val intensityLevel: Int = 1,
    val outcome: OutcomeType = OutcomeType.Neutral,
    val isSavingLog: Boolean = false,
    val showDropDownMenu: Boolean = false,
    val showDeleteDialog: Boolean = false,
    val showDatePicker: Boolean = false,
    val showTimePicker: Boolean = false,
    val epochMillis: Long = System.currentTimeMillis()
)