package com.gongfu.recoverycompanion.logs.presentation.add_logentry

import androidx.lifecycle.ViewModel
import com.gongfu.recoverycompanion.logs.domain.use_case.LogUseCases
import com.gongfu.recoverycompanion.logs.presentation.loglist.LogListEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class AddLogViewModel(
    private val logUseCases: LogUseCases
): ViewModel() {
    private val _state = MutableStateFlow(AddLogState())
    val state = _state.asStateFlow()

    private val _events = Channel<LogListEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: AddLogAction) {
        when (action) {
            AddLogAction.OnBackClick -> Unit
            AddLogAction.OnSaveClick -> TODO()
        }
    }
}