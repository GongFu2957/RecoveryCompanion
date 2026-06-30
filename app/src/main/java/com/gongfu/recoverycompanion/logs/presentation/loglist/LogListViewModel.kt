package com.gongfu.recoverycompanion.logs.presentation.loglist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gongfu.recoverycompanion.R
import com.gongfu.recoverycompanion.logs.domain.repository.LogRepository
import com.gongfu.recoverycompanion.logs.domain.util.LogOrder
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LogListViewModel(
    private val logRepository: LogRepository
): ViewModel() {
    private val _state = MutableStateFlow(LogListState())
    val state = _state.asStateFlow()

    private val _events = Channel<LogListEvent>()
    val events = _events.receiveAsFlow()

    init {
        getLogs(_state.value.logOrder)
    }

    fun onAction(action: LogListAction) {
        when (action) {
            is LogListAction.CreateLog -> Unit
            is LogListAction.OnLogClick -> selectedLog(action.logId)
            is LogListAction.Order -> getLogs(action.order)
            is LogListAction.OnFilterClick -> toggleFilter()
            LogListAction.OnDropDownDismiss -> {
                _state.update { it.copy(showDropDownMenu = false) }
            }
            LogListAction.OnDropDownExpand -> {
                _state.update { it.copy(showDropDownMenu = true) }
            }
            LogListAction.OnSettingsClick -> {}
        }
    }

    private fun toggleFilter() {
        _state.update {
            it.copy(isFilterOpen = !_state.value.isFilterOpen)
        }
    }
    private fun selectedLog(logById: Long) {
        _state.update { it.copy(selectedLogById = logById ) }

        viewModelScope.launch {
            try {
                logRepository.getLogById(id = logById)
            } catch (e: Exception) {
                _events.send(LogListEvent.Error(R.string.failed_to_fetch_log))
            }
        }
    }
    private fun getLogs(logOrder: LogOrder) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    logOrder = logOrder
                )
            }

            try {
                logRepository.getLogs(logOrder)
                    .collect { logList ->
                        _state.update {
                            it.copy(
                            logs = logList,
                            isLoading = false
                            )
                        }
                    }

            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false) }
                _events.send(LogListEvent.Error(R.string.failed_to_fetch_logs))
            }
        }
    }
}
