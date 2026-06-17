package com.gongfu.recoverycompanion.core.presentation.designsystem.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.gongfu.recoverycompanion.ui.theme.RecoveryCompanionTheme
import java.time.Instant
import java.time.ZoneId

@Composable
fun DaterPickerModal(
    currentDateTimeInMillis: Long,
    onDateSelected: (Long) -> Unit,
    onDismiss: () -> Unit
) {
    val localDate = Instant.ofEpochMilli(currentDateTimeInMillis)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    val utcMidnightMillis = localDate
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = utcMidnightMillis,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis <= System.currentTimeMillis()
            }
        }
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
            currentDateTimeInMillis = System.currentTimeMillis()
        )
    }
}