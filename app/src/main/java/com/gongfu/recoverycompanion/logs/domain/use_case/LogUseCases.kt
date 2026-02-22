package com.gongfu.recoverycompanion.logs.domain.use_case

data class LogUseCases(
    val getLogs: GetLogs,
    val deleteLog: DeleteLog,
    val addLog: AddLog,
    val getLog: GetLog
)
