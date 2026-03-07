package com.gongfu.recoverycompanion.logs.presentation.utils

sealed class LogDestinations(val route: String) {
    data object LogList : LogDestinations("log_list")
    data object AddLog : LogDestinations("add_log")
    data object LogDetail : LogDestinations("log_detail/{logId}") {
        fun createRoute(logId: String) = "log_detail/${logId}"
    }
}