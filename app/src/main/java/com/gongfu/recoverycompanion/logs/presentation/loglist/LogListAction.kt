package com.gongfu.recoverycompanion.logs.presentation.loglist

sealed interface LogListAction {
    data class OnLogClick(val log: Long) : LogListAction
    data object CreateLog : LogListAction
    data object OnFilterClick : LogListAction
    data object OnMoreMenuClick : LogListAction
}