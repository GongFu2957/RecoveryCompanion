package com.gongfu.recoverycompanion.logs.presentation.loglist

sealed interface LogListAction {
    data object OnLogClick : LogListAction
    data object CreateLog : LogListAction
}