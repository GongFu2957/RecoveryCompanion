package com.gongfu.recoverycompanion.logs.presentation.add_edit_logentry

import androidx.compose.foundation.text.input.TextFieldState

data class AddEditLogState(
    val logId: Long? = null,
    val title: TextFieldState = TextFieldState(),
    val description: TextFieldState = TextFieldState(),
    val trigger: TextFieldState = TextFieldState(),
    val location: TextFieldState = TextFieldState(),
    val intensityLevel: Int = 0,
    val bodyResponse: TextFieldState = TextFieldState(),
    val outcome: Boolean = false,
    val isSavingLog: Boolean = false
)