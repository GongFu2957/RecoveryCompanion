package com.gongfu.recoverycompanion.logs.presentation.loglist

import com.gongfu.recoverycompanion.logs.domain.util.LogOrder

sealed interface LogListAction {
    data class OnLogClick(val logId: Long) : LogListAction

    data object CreateLog : LogListAction
    data object OnFilterClick : LogListAction

    data object OnSettingsClick : LogListAction
    data object OnDropDownExpand : LogListAction
    data object OnDropDownDismiss : LogListAction

    data class Order(val order: LogOrder) : LogListAction
}