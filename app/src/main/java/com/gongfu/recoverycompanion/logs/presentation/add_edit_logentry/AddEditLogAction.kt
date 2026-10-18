package com.gongfu.recoverycompanion.logs.presentation.add_edit_logentry

import com.gongfu.recoverycompanion.logs.domain.model.OutcomeType

sealed interface AddEditLogAction {
    data class OnSaveClick(
        val title: String,
        val epochMillis: Long,
        val description: String,
        val trigger: String,
        val location: String,
        val bodyResponse: String,
        val intensityLevel: Int,
        val outcome: OutcomeType
    ) : AddEditLogAction
    data class IntensityChanged(val level: Int) : AddEditLogAction
    data class OutcomeChanged(val outcome: OutcomeType) : AddEditLogAction
    data object OnDeleteClick : AddEditLogAction
    data object OnDeletePermanently : AddEditLogAction
    data object OnBackClick : AddEditLogAction
    data object OnDismissDelete : AddEditLogAction
    data object OnDropDownExpand : AddEditLogAction
    data object OnDropDownDismiss : AddEditLogAction
    data object OnDatePickerClick : AddEditLogAction
    data class OnDateSelected(val newMillis: Long) : AddEditLogAction
    data object OnDateDismiss : AddEditLogAction
    data object OnTimePickerClick : AddEditLogAction
    data object OnTimeDismiss : AddEditLogAction
    data class OnTimeSelected(val newMillis: Long) : AddEditLogAction
}
