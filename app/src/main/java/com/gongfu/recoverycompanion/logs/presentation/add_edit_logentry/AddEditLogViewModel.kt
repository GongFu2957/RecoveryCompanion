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
import java.time.Instant
import java.time.ZoneId

class AddEditLogViewModel(
    private val logRepository: LogRepository,
    savedStateHandle: SavedStateHandle
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
                    epochMillis = action.epochMillis,
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
                if (_state.value.logId != null) {
                    _state.update {
                        it.copy(
                            showDropDownMenu = false,
                            showDeleteDialog = true
                        )
                    }
                }
            }
            is AddEditLogAction.OnDeletePermanently -> {
                deleteLogPermanently()
            }
            is AddEditLogAction.OnDismissDelete -> {
                _state.update { it.copy(showDeleteDialog = false) }
            }
            is AddEditLogAction.OnDatePickerClick -> {
                _state.update { it.copy(showDatePicker = true) }
            }
            is AddEditLogAction.OnDateSelected -> {
                val currentEpochMillis = _state.value.epochMillis
                val newEpochMillis = action.newMillis
                onUpdateSelectedDate(currentEpochMillis, newEpochMillis)
            }
            is AddEditLogAction.OnDateDismiss -> {
                _state.update { it.copy(showDatePicker = false) }
            }
            is AddEditLogAction.OnTimeClick -> {}
            AddEditLogAction.OnDropDownDismiss -> {
                _state.update { it.copy(showDropDownMenu = false,)}
            }
            AddEditLogAction.OnDropDownExpand -> {
                _state.update { it.copy(showDropDownMenu = true) }
            }
        }
    }

    private fun deleteLogPermanently() {
        val logId = _state.value.logId ?: return

        viewModelScope.launch {
            try {
                val currentLog = logRepository.getLogById(logId)
                currentLog?.let { log ->
                    logRepository.deleteLog(log)
                }
                _events.send(AddEditLogEvent.ShowDeleteSuccessful(R.string.add_log_delete_successful))
                _events.send(AddEditLogEvent.NavigateBack)
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
                            epochMillis = log.timestamp
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
        epochMillis: Long,
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

        viewModelScope.launch {
            try {
                val log = LogEntry(
                    id = state.value.logId ?: 0L,
                    timestamp = epochMillis,
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

    private fun onUpdateSelectedDate(epochMillis: Long, newEpochMillis: Long) {
        val currentInstant = Instant.ofEpochMilli(epochMillis)
        val currentLocal = currentInstant.atZone(ZoneId.systemDefault())

        // DatePicker gives UTC midnight for the selected date
        // Convert DatePicker selected date to a LocalDate in current timezone
        val pickerSelectedLocalDate = Instant.ofEpochMilli(newEpochMillis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        // Combine the new date with existing time
        val newLocalDateTime = pickerSelectedLocalDate
            .atTime(currentLocal.toLocalTime())

        val updatedEpochMillis = newLocalDateTime
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        // Update state value
        _state.update { it.copy(
            epochMillis = updatedEpochMillis,
            showDatePicker = false
        ) }
    }
}

