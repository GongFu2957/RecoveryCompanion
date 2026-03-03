package com.gongfu.recoverycompanion.logs.presentation.loglist

import androidx.lifecycle.ViewModel
import com.gongfu.recoverycompanion.logs.domain.use_case.LogUseCases
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class LogListViewModel(
    private val logUseCases: LogUseCases
): ViewModel() {
    private val _state = MutableStateFlow(LogListState())
    val state = _state.asStateFlow()

    private val _events = Channel<LogListEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: LogListAction) {
        when (action) {
            is LogListAction.CreateLog -> TODO()
            is LogListAction.OnLogClick -> TODO()
            LogListAction.OnDeleteClick -> TODO()
            LogListAction.OnSettingsClick -> TODO()
            LogListAction.OnSortDateAscending -> TODO()
            LogListAction.OnSortDateDescending -> TODO()
            LogListAction.OnSortIntensityLevelAscending -> TODO()
            LogListAction.OnSortIntensityLevelDescending -> TODO()
        }
    }
}