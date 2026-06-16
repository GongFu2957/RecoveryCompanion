package com.gongfu.recoverycompanion.core.presentation.designsystem.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.gongfu.recoverycompanion.ui.theme.RecoveryCompanionTheme
import java.util.TimeZone

@Composable
fun DaterPickerModal(
    currentDateInMillis: Long,
    onDateSelected: (Long) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = currentDateInMillis
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton( onClick = {
                datePickerState.selectedDateMillis?.let {
                    onDateSelected(it)
                    onDismiss()
                }
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@PreviewLightDark
@Composable
private fun DatePickerTest() {
    RecoveryCompanionTheme {
        DaterPickerModal(
            onDateSelected = {},
            onDismiss = {},
            currentDateInMillis = System.currentTimeMillis()
        )
    }
}