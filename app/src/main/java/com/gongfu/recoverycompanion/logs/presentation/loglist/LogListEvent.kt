package com.gongfu.recoverycompanion.logs.presentation.loglist

sealed interface LogListEvent {
    data class Error(val error: Int): LogListEvent
}