package com.gongfu.recoverycompanion.logs.presentation.loglist

import com.gongfu.recoverycompanion.logs.domain.model.LogEntry

sealed interface LogListAction {
    data class OnLogClick(val logId: Long) : LogListAction

    data object CreateLog : LogListAction

    data object OnSettingsClick : LogListAction

    data object OnDeleteClick : LogListAction

    data object OnSortDateAscending : LogListAction

    data object OnSortDateDescending : LogListAction

    data object OnSortIntensityLevelAscending : LogListAction

    data object OnSortIntensityLevelDescending : LogListAction
}