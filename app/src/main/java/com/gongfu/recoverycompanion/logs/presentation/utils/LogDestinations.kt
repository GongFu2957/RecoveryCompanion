package com.gongfu.recoverycompanion.logs.presentation.utils

sealed class LogDestinations(val route: String) {
    data object LogList : LogDestinations("log_list")
    data object AddLog : LogDestinations("add_log/{logId?}") {
        fun createRoute(logId: Long? = null) = if (logId != null) "add_log/$logId" else "add_log"
    }
}
