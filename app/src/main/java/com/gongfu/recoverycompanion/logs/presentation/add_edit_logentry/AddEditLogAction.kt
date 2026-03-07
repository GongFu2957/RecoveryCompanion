package com.gongfu.recoverycompanion.logs.presentation.add_edit_logentry

sealed interface AddEditLogAction {
    data object OnBackClick : AddEditLogAction
    data object OnSaveClick : AddEditLogAction
}