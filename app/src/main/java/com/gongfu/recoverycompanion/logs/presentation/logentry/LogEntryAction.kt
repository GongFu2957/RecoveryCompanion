package com.gongfu.recoverycompanion.logs.presentation.logentry

sealed interface LogEntryAction {
    data object OnLogClick : LogEntryAction
    data object CreateLog : LogEntryAction
}