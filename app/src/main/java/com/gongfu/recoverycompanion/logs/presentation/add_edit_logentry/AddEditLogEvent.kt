package com.gongfu.recoverycompanion.logs.presentation.add_edit_logentry

sealed interface AddEditLogEvent {
    data class Error(val error: String) : AddEditLogEvent
    data object SaveSuccessful : AddEditLogEvent
}