package com.gongfu.recoverycompanion.logs.presentation.add_edit_logentry.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gongfu.recoverycompanion.ui.theme.RecoveryCompanionTheme

@Composable
fun LogEntryTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    error: String?,
    modifier: Modifier = Modifier
    ) {
    var isFieldFocused by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                label = { Text(label) },
                isError = error != null,
                supportingText = {
                    error?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                },
                textStyle = LocalTextStyle.current.copy(
                    MaterialTheme.colorScheme.onSurface
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { isFieldFocused = it.isFocused },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = if (isFieldFocused)
                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.08f)
                    else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.04f)
                ),
            )
            }
        }
}

@Preview
@Composable
private fun LogEntryTextFieldPreview() {
    RecoveryCompanionTheme {
        LogEntryTextField(
            label = "Title",
            value = "",
            onValueChange = {},
            error = null,
            modifier = Modifier.fillMaxWidth()
        )
    }
}