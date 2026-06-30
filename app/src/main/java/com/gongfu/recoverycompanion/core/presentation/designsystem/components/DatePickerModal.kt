package com.gongfu.recoverycompanion.core.presentation.designsystem.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.gongfu.recoverycompanion.ui.theme.RecoveryCompanionTheme
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset

@Composable
fun DaterPickerModal(
    currentDateTimeInMillis: Long,
    onDateSelected: (newEpochMillis: Long) -> Unit,
    onDismiss: () -> Unit
) {
    // Current timezone and local date
    val zoneId = ZoneId.systemDefault()
    val todayLocalDate = remember(zoneId) { LocalDate.now(zoneId)}

    // Setting the picker value to local current date in utc epoch milli
    val initialPickerMillis = remember(currentDateTimeInMillis, zoneId) {
        Instant.ofEpochMilli(currentDateTimeInMillis)
            .atZone(zoneId)
            .toLocalDate()
            .atStartOfDay(zoneId)
            .toInstant()
            .toEpochMilli()
    }


    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialPickerMillis,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val selectedLocalDate = Instant.ofEpochMilli(utcTimeMillis)
                    .atZone(ZoneOffset.UTC)
                    .toLocalDate()
                return !selectedLocalDate.isAfter(todayLocalDate)
            }

            override fun isSelectableYear(year: Int): Boolean {
                return year <= todayLocalDate.year
            }
        }
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton( onClick = {
                datePickerState.selectedDateMillis?.let { pickerUtcMillis ->
                    //picker returning utc midnight of selected calendar date
                    val selectedLocalDate = Instant.ofEpochMilli(pickerUtcMillis)
                        .atZone(ZoneOffset.UTC)
                        .toLocalDate()

                    //convert to epoch millis at local midnight
                    val localMidnightMillis = selectedLocalDate
                        .atStartOfDay(zoneId)
                        .toInstant()
                        .toEpochMilli()
                    onDateSelected(localMidnightMillis)
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