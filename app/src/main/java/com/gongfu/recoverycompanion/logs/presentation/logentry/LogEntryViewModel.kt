package com.gongfu.recoverycompanion.logs.presentation.logentry

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class LogEntryViewModel(): ViewModel() {
    private val _state = MutableStateFlow(LogEntryState())

    val state = _state
}