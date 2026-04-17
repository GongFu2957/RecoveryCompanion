package com.gongfu.recoverycompanion.logs.presentation.add_edit_logentry

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gongfu.recoverycompanion.R
import com.gongfu.recoverycompanion.logs.domain.model.LogEntry
import com.gongfu.recoverycompanion.logs.domain.repository.LogRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddEditLogViewModel(
    private val logRepository: LogRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _state = MutableStateFlow(AddEditLogState())
    val state = _state.asStateFlow()

    private val _events = Channel<AddEditLogEvent>()
    val events = _events.receiveAsFlow()

    init {
        savedStateHandle.get<String>("logId")?.let { logIdStr ->
            val logId = logIdStr.toLongOrNull()
            if (logId != null) {
                loadExistingLog(logId)
            }
        }
    }

    fun onAction(action: AddEditLogAction) {
        when (action) {
            is AddEditLogAction.OnBackClick -> Unit
            is AddEditLogAction.OnSaveClick -> {
                _state.update { it.copy(isSavingLog = true) }
                onSaveClicked(
                    title = action.title,
                    description = action.description,
                    trigger = action.trigger,
                    location = action.location,
                    bodyResponse = action.bodyResponse,
                    intensityLevel = action.intensityLevel,
                    outcome = action.outcome
                )
            }

            is AddEditLogAction.IntensityChanged -> {
                _state.update { it.copy(intensityLevel = action.level) }
            }

            is AddEditLogAction.OutcomeChanged -> {
                _state.update { it.copy(outcome = action.outcome) }
            }
            is AddEditLogAction.OnDeleteClick -> {
                showDeleteDialog()
            }
        }
    }

    private fun showDeleteDialog() {
        if (_state.value.logId == null) return

        viewModelScope.launch {
           try {
               _state.update { it.copy(openDeleteDialog = true) }
           } catch(e: Exception) {

           }
        }
    }

    fun deleteLogPermanently(logId: Long?) {
        if (logId == null) return

        viewModelScope.launch {
            try {
                val currentLog = logRepository.getLogById(logId)
                currentLog?.let { log ->
                    logRepository.deleteLog(log)
                }
            } catch (e: Exception) {
                _events.send(AddEditLogEvent.Error(R.string.add_log_delete_error))
                TODO("Implement Catching exception with Timber Logging")
            }
        }
    }

    private fun loadExistingLog(logId: Long?) {
        if (logId == null) return

        viewModelScope.launch {
            try {
                val existingLog = logRepository.getLogById(logId)
                existingLog?.let { log ->
                    _state.update { current ->
                        current.copy(
                            selectedLog = log,
                            logId = log.id,
                            intensityLevel = log.intensityLevel,
                            outcome = log.outcome,
                        )
                    }
                }
            } catch (e: Exception) {
                _events.send(AddEditLogEvent.Error(R.string.failed_to_load_log))
                TODO("Implement Catching exception with Timber Logging")
            }
        }
    }
    fun onSaveClicked(
        title: String,
        description: String,
        trigger: String,
        location: String,
        bodyResponse: String,
        intensityLevel: Int,
        outcome: Boolean
    ) {
        val errors = buildMap {
            if (title.isBlank()) put(LogField.TITLE, "Title cannot be empty")
            if (description.isBlank()) put(LogField.DESCRIPTION, "Description cannot be empty")
            if (trigger.isBlank()) put(LogField.TRIGGER, "Trigger cannot be empty")
            if (location.isBlank()) put(LogField.LOCATION, "Location cannot be empty")
            if (bodyResponse.isBlank()) put(LogField.BODY_RESPONSE, "Body Response cannot be empty")
        }

        if (errors.isNotEmpty()) {
            _state.update { it.copy(fieldErrors = errors, isSavingLog = false) }
            return
        }

        _state.update { it.copy(isSavingLog = true, fieldErrors = emptyMap()) }

        val timestamp = _state.value.selectedLog?.timestamp

        viewModelScope.launch {
            try {
                val log = LogEntry(
                    id = state.value.logId ?: 0L,
                    timestamp = timestamp ?: System.currentTimeMillis(),
                    title = title,
                    description = description,
                    trigger = trigger,
                    location = location,
                    intensityLevel = intensityLevel,
                    bodyResponse = bodyResponse,
                    outcome = outcome
                )
                logRepository.insertLog(log)
                val saveUpdateToastText = if (_state.value.logId != null) R.string.add_log_save_update else R.string.add_log_save_successful
                _events.send(AddEditLogEvent.ShowSaveSuccessful(saveUpdateToastText))
                _events.send(AddEditLogEvent.NavigateBack)
            } catch (e: Exception) {
                _events.send(AddEditLogEvent.ShowSaveError(R.string.add_log_save_error))
                TODO("Implement Catching exception with Timber Logging")
            } finally {
                _state.update { it.copy(isSavingLog = false) }
            }
        }
    }
}

