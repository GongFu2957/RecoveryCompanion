package com.gongfu.recoverycompanion.logs.presentation.add_edit_logentry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gongfu.recoverycompanion.logs.domain.model.LogEntry
import com.gongfu.recoverycompanion.logs.domain.use_case.LogUseCases
import com.gongfu.recoverycompanion.logs.presentation.loglist.LogListEvent
import com.gongfu.recoverycompanion.logs.presentation.utils.currentTimeToEpochMillis
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddEditLogViewModel(
    private val logUseCases: LogUseCases
): ViewModel() {
    private val _state = MutableStateFlow(AddEditLogState())
    val state = _state.asStateFlow()

    private val _events = Channel<LogListEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: AddEditLogAction) {
        when (action) {
            AddEditLogAction.OnBackClick -> Unit
            AddEditLogAction.OnSaveClick -> saveLog()
        }
    }

    private fun saveLog() {
        viewModelScope.launch {
            _state.update { it.copy(isSavingLog = true) }
            logUseCases.addLog(
                LogEntry(
                    id = 0L,
                    timestamp = currentTimeToEpochMillis(),
                    title = "${_state.value.title.text}",
                    description = "${_state.value.description.text}",
                    trigger = "${_state.value.trigger.text}",
                    location = "${_state.value.location.text}",
                    intensityLevel = _state.value.intensityLevel,
                    bodyResponse = "${_state.value.bodyResponse.text}",
                    outcome = _state.value.outcome
                )
            )
            _state.update { it.copy(isSavingLog = false) }
        }
    }
}
