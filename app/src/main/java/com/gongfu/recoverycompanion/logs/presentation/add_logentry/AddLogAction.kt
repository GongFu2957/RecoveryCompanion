package com.gongfu.recoverycompanion.logs.presentation.add_logentry

sealed interface AddLogAction {
    data object OnBackClick : AddLogAction
    data object OnSaveClick : AddLogAction
}