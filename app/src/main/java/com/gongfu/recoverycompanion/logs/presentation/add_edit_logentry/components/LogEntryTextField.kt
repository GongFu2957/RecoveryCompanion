package com.gongfu.recoverycompanion.logs.presentation.add_edit_logentry.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gongfu.recoverycompanion.R
import com.gongfu.recoverycompanion.ui.theme.RecoveryCompanionTheme
import com.gongfu.recoverycompanion.ui.theme.helpQuestion

@Composable
fun LogEntryTextField(
    label: String,
    state: TextFieldState,
    modifier: Modifier = Modifier,
    endIcon: ImageVector? = null,
    error: String? = null,
    supportingText: String? = null,
    helpText: String? = null,
    onHelpClick: (() -> Unit)? = null,
    lineLimits: TextFieldLineLimits = TextFieldLineLimits.SingleLine,
    ) {
    var isFieldFocused by remember { mutableStateOf(false) }
    var expandedHelp by remember { mutableStateOf(false) }

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
                state = state,
                label = { Text(text = label) },
                isError = error != null,
                supportingText = {
                    when {
                        error != null -> Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error
                        )
                        supportingText != null -> Text(
                            text = supportingText,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                trailingIcon = {
                    endIcon?.let { icon ->
                        Box {
                            IconButton(
                                onClick = {
                                    expandedHelp = true
                                    onHelpClick?.invoke()
                                },
                            ) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = stringResource(R.string.help_text),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                            DropdownMenu(
                                expanded = expandedHelp,
                                onDismissRequest = {
                                    expandedHelp = false
                                },
                                shape = RoundedCornerShape(12.dp),
                                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                                tonalElevation = 2.dp,
                                shadowElevation = 4.dp
                            ) {
                                helpText?.let {
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                text = it,
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        },
                                        onClick = { expandedHelp = false }
                                    )
                                }
                            }
                        }
                    }
                },
                textStyle = LocalTextStyle.current.copy(
                    MaterialTheme.colorScheme.onSurface
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 350.dp)
                    .onFocusChanged { isFieldFocused = it.isFocused },
                lineLimits = lineLimits,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = if (isFieldFocused)
                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.08f)
                    else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.04f),
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    ),
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun LogEntryTextFieldPreview() {
    RecoveryCompanionTheme {
        LogEntryTextField(
            label = "Title",
            state = TextFieldState(),
            lineLimits = TextFieldLineLimits.MultiLine(
                minHeightInLines = 3,
                maxHeightInLines = 3
            ),
            error = null,
            supportingText = "You got it boss",
            endIcon = helpQuestion,
            onHelpClick = { },
            modifier = Modifier.fillMaxWidth(),
            helpText = "You need a couple dollars in their aye?"
        )
    }
}