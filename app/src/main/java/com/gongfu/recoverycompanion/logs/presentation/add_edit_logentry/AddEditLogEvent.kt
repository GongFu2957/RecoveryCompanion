package com.gongfu.recoverycompanion.logs.presentation.add_edit_logentry

sealed interface AddEditLogEvent {
    data class ShowSaveSuccessful(val message: Int) : AddEditLogEvent
    data class ShowDeleteSuccessful(val message: Int) : AddEditLogEvent
    data class ShowSaveError(val error: Int) : AddEditLogEvent
    data class Error(val error: Int) : AddEditLogEvent
     data object NavigateBack : AddEditLogEvent
}