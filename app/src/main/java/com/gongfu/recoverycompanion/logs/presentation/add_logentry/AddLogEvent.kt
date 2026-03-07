package com.gongfu.recoverycompanion.logs.presentation.add_logentry

sealed interface AddLogEvent {
    data class Error(val error: String) : AddLogEvent
    data object SaveSuccessful : AddLogEvent
}