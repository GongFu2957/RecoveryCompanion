package com.gongfu.recoverycompanion.logs.presentation.add_edit_logentry


enum class LogField { TITLE, DESCRIPTION, TRIGGER, LOCATION, BODY_RESPONSE }
data class AddEditLogState(
    val logId: Long? = null,
    val fieldErrors: Map<LogField, String> = emptyMap(),
    val intensityLevel: Int = 1,
    val outcome: Boolean = false,
    val isSavingLog: Boolean = false
)