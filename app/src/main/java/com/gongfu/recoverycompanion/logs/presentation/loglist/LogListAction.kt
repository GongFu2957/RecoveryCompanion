package com.gongfu.recoverycompanion.logs.presentation.loglist

import com.gongfu.recoverycompanion.logs.domain.util.LogOrder

sealed interface LogListAction {
    data class OnLogClick(val logId: Long) : LogListAction

    data object CreateLog : LogListAction
    data object OnFilterClick : LogListAction

    data object OnSettingsClick : LogListAction

    data object OnDeleteClick : LogListAction

    data object OnSortDateAscending : LogListAction

    data object OnSortDateDescending : LogListAction

    data object OnSortIntensityLevelAscending : LogListAction

    data object OnSortIntensityLevelDescending : LogListAction

    data class Order(val order: LogOrder) : LogListAction
}