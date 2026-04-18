package com.gongfu.recoverycompanion.logs.presentation.add_edit_logentry

sealed interface AddEditLogAction {
    data class OnSaveClick(
        val title: String,
        val description: String,
        val trigger: String,
        val location: String,
        val bodyResponse: String,
        val intensityLevel: Int,
        val outcome: Boolean
    ) : AddEditLogAction

    data class IntensityChanged(val level: Int) : AddEditLogAction
    data class OutcomeChanged(val outcome: Boolean) : AddEditLogAction
    data object OnDeleteClick : AddEditLogAction

    data object OnDeletePermanently : AddEditLogAction
    data object OnBackClick : AddEditLogAction
    data object DismissDelete : AddEditLogAction
}
