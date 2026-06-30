package com.gongfu.recoverycompanion.core.presentation.designsystem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.gongfu.recoverycompanion.ui.theme.RecoveryCompanionTheme
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerModal(
    currentDateTimeInMillis: Long,
    onTimeSelected: (newEpochMillis: Long) -> Unit,
    onDismiss: () -> Unit,
) {
    // Current timezone and local time
    val zoneId = ZoneId.systemDefault()
    val todayLocalDateTime = remember(currentDateTimeInMillis) {
        Instant.ofEpochMilli(currentDateTimeInMillis)
            .atZone(zoneId)
            .toLocalDateTime()
    }

    val timePickerState = rememberTimePickerState(
        initialHour = todayLocalDateTime.hour,
        initialMinute = todayLocalDateTime.minute,
        //TODO update 24 hour functionality
        is24Hour = false
    )

    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.wrapContentSize()
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Select Time",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(20.dp))

                TimePicker(state = timePickerState)

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.align(Alignment.End)
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    TextButton(
                        onClick = {
                            val selectedTime = LocalTime.of(timePickerState.hour, timePickerState.minute)
                            val newDateTime = todayLocalDateTime
                                .toLocalDate()
                                .atTime(selectedTime)
                                .atZone(zoneId)
                                .toInstant()
                                .toEpochMilli()

                            onTimeSelected(newDateTime)
                            onDismiss()
                        }
                    ) {
                        Text("OK")
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun TimePickerModalTest() {
    RecoveryCompanionTheme {
        TimePickerModal(
            onTimeSelected = {},
            onDismiss = {},
            currentDateTimeInMillis = System.currentTimeMillis()
        )
    }
}