package com.gongfu.recoverycompanion.logs.presentation.add_edit_logentry

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gongfu.recoverycompanion.R
import com.gongfu.recoverycompanion.core.presentation.designsystem.components.DaterPickerModal
import com.gongfu.recoverycompanion.core.presentation.designsystem.components.RecoveryTopAppBar
import com.gongfu.recoverycompanion.core.presentation.designsystem.components.TimePickerModal
import com.gongfu.recoverycompanion.logs.presentation.add_edit_logentry.components.LogEntryTextField
import com.gongfu.recoverycompanion.logs.presentation.loglist.components.DropDownItem
import com.gongfu.recoverycompanion.logs.presentation.utils.formatEpochMillis
import com.gongfu.recoverycompanion.ui.theme.RecoveryCompanionTheme
import com.gongfu.recoverycompanion.ui.theme.Typography
import com.gongfu.recoverycompanion.ui.theme.helpQuestion
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddEditLogScreenRoot(
    onBack: () -> Unit,
    viewModel: AddEditLogViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(viewModel.events) {
        viewModel.events.collect { event ->
            when (event) {
                is AddEditLogEvent.Error -> {
                    Toast.makeText(
                        context,
                        event.error,
                        Toast.LENGTH_LONG
                    )
                }
                is AddEditLogEvent.ShowSaveError -> {
                    Toast.makeText(
                        context,
                        event.error,
                        Toast.LENGTH_LONG
                    ).show()
                }
                is AddEditLogEvent.ShowSaveSuccessful -> {
                    Toast.makeText(
                        context,
                        event.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
                is AddEditLogEvent.ShowDeleteSuccessful-> {
                    Toast.makeText(
                        context,
                        event.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
                is AddEditLogEvent.NavigateBack -> {
                    onBack()
                }
            }

        }
    }

    AddEditLogScreen(
        state = state,
        onAction = { action ->
            when (action) {
                AddEditLogAction.OnBackClick -> if (!state.isSavingLog) onBack()
                else -> viewModel.onAction(action)
            }
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AddEditLogScreen(
    state: AddEditLogState,
    onAction: (AddEditLogAction) -> Unit,
    modifier: Modifier = Modifier
    ) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = topAppBarState
    )
    val titleState = rememberTextFieldState()
    val descriptionState = rememberTextFieldState()
    val triggerState = rememberTextFieldState()
    val locationState = rememberTextFieldState()
    val bodyResponseState = rememberTextFieldState()
    //Repopulate Fields to Edit Log
    LaunchedEffect(state.selectedLog?.id) {
        println("LaunchedEffect triggered for log ${state.selectedLog?.id}")
        state.selectedLog?.let { log ->
            titleState.edit { replace(0, length, log.title) }
            descriptionState.edit { replace(0, length, log.description) }
            triggerState.edit { replace(0, length, log.trigger) }
            locationState.edit { replace(0, length, log.location) }
            bodyResponseState.edit { replace(0, length, log.bodyResponse) }
            println("Fields updated with title='${log.title}'")
        }
    }

    // Delete Dialog
    if (state.showDeleteDialog) {
        BasicAlertDialog(
            // Close Dialog when clicking outside the dialog.
            onDismissRequest = { onAction(AddEditLogAction.OnDismissDelete) }
        ) {
            Surface(
                modifier = Modifier
                    .widthIn(max = 300.dp)
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.large,
                color = MaterialTheme.colorScheme.surfaceContainer,
                tonalElevation = AlertDialogDefaults.TonalElevation,
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(R.string.add_log_delete_prompt),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(
                            onClick = { onAction(AddEditLogAction.OnDismissDelete) },
                        ) {
                            Text(text = stringResource(R.string.add_log_delete_dismiss))
                        }
                        TextButton(
                            onClick = { onAction(AddEditLogAction.OnDeletePermanently) },
                        ) {
                            Text(
                                text = stringResource(R.string.add_log_delete_confirm),
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }

    // Show Date Picker
    if (state.showDatePicker) {
        DaterPickerModal(
            currentDateTimeInMillis = state.epochMillis,
            onDateSelected = { millis ->
                onAction(AddEditLogAction.OnDateSelected(millis))
            },
            onDismiss = { onAction(AddEditLogAction.OnDateDismiss) }
        )
    }
    // Show Time Picker
    if (state.showTimePicker) {
        TimePickerModal(
            currentDateTimeInMillis = state.epochMillis,
            onTimeSelected = { millis ->
                onAction(AddEditLogAction.OnTimeSelected(millis))
            },
            onDismiss = { onAction(AddEditLogAction.OnTimeDismiss) }
        )
    }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            RecoveryTopAppBar(
                showBackButton = true,
                title = state.selectedLog?.title ?: stringResource(R.string.new_log_entry),
                menuItems = if (state.selectedLog != null) {
                    listOf(
                        DropDownItem(
                            icon = Icons.Default.Delete,
                            title = stringResource(R.string.delete)
                        )
                    )
                } else emptyList(),
                isDropDownOpen = state.showDropDownMenu,
                onDropDownExpand = { onAction(AddEditLogAction.OnDropDownExpand) },
                onDropDownDismiss = { onAction(AddEditLogAction.OnDropDownDismiss) },
                onMenuItemClick = { onAction(AddEditLogAction.OnDeleteClick) },
                scrollBehavior = scrollBehavior,
                onBackClick = { onAction(AddEditLogAction.OnBackClick) }
            )
        },
        contentWindowInsets = WindowInsets.safeDrawing
    ) { scaffoldPadding ->
        LazyColumn(  // Scrollable form
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
                .consumeWindowInsets(scaffoldPadding)
                .windowInsetsPadding(WindowInsets.ime),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                // Title
                LogEntryTextField(
                    label = stringResource(R.string.add_log_title),
                    state = titleState,
                    error = state.fieldErrors[LogField.TITLE]
                )
                // Date/Time
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = formatEpochMillis(state.epochMillis),
                        onValueChange = {},
                        label = { Text("Date & Time") },
                        singleLine = true,
                        readOnly = true,
                        trailingIcon = {
                            Row {
                                Icon(
                                    Icons.Default.EditCalendar,
                                    contentDescription = "Change Date",
                                    modifier = Modifier
                                        .clickable { onAction(AddEditLogAction.OnDatePickerClick) }
                                        .padding(end = 12.dp)
                                )
                                Icon(
                                    Icons.Default.AccessTime,
                                    contentDescription = "Change Time",
                                    modifier = Modifier
                                        .clickable { onAction(AddEditLogAction.OnTimePickerClick) }
                                        .padding(end = 12.dp)
                                )
                            }
                        },
                        textStyle = LocalTextStyle.current.copy(
                           color = MaterialTheme.colorScheme.onSurface,
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(
                                alpha = 0.04f
                            ),
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .widthIn(max = 350.dp)
                    )
                }
                // Description
                LogEntryTextField(
                    label = stringResource(R.string.add_log_description),
                    state = descriptionState,
                    lineLimits = TextFieldLineLimits.MultiLine(
                        minHeightInLines = 4,
                        maxHeightInLines = 4
                    ),
                    endIcon = helpQuestion,
                    helpText = stringResource(R.string.add_log_description_help),
                    error = state.fieldErrors[LogField.DESCRIPTION]
                )

                // Trigger
                LogEntryTextField(
                    label = stringResource(R.string.add_log_trigger),
                    state = triggerState,
                    endIcon = helpQuestion,
                    helpText = stringResource(R.string.add_log_trigger_help),
                    error = state.fieldErrors[LogField.TRIGGER]
                )

                // Location
                LogEntryTextField(
                    label = stringResource(R.string.add_log_location),
                    state = locationState,
                    endIcon = helpQuestion,
                    helpText = stringResource(R.string.add_log_location_help),
                    error = state.fieldErrors[LogField.LOCATION]
                )

                // Intensity Level (Slider or picker - placeholder)
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.outline
                    ),
                    modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.add_log_intensity_level),
                            color = MaterialTheme.colorScheme.onBackground,
                            style = Typography.labelMedium,
                        )
                        Slider(
                            value = state.intensityLevel.toFloat(),
                            onValueChange = { newIntensityLevel ->
                                onAction(AddEditLogAction.IntensityChanged(newIntensityLevel.toInt()))
                            },
                            valueRange = 1f..10f,
                            steps = 8
                        )

                       // Steps labels
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, end = 2.dp)
                        ) {
                            repeat(10) { index ->
                                Text(
                                  text = "${1 + index}",
                                  fontSize = 12.sp,
                                  color = MaterialTheme.colorScheme.onBackground,
                                  modifier = Modifier
                                      .wrapContentWidth(Alignment.CenterHorizontally),
                                  textAlign = TextAlign.Center
                              )
                            }
                        }
                    }
                }

                // Body Response
                LogEntryTextField(
                    label = stringResource(R.string.add_log_body_response),
                    state = bodyResponseState,
                    endIcon = helpQuestion,
                    helpText = stringResource(R.string.add_log_body_response_help),
                    error = state.fieldErrors[LogField.BODY_RESPONSE]
                )

                // Outcome Toggle
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.add_log_healthy_outcome),
                        fontStyle = FontStyle.Italic
                    )
                    Switch(
                        checked = state.outcome,
                        onCheckedChange = { newOutcome ->
                            onAction(AddEditLogAction.OutcomeChanged(newOutcome))
                        },
                    )
                }
                // Save Button
                Button(
                    onClick = { onAction(AddEditLogAction.OnSaveClick(
                        title = titleState.text.toString(),
                        epochMillis = state.epochMillis,
                        description = descriptionState.text.toString(),
                        trigger = triggerState.text.toString(),
                        location = locationState.text.toString(),
                        bodyResponse = bodyResponseState.text.toString(),
                        intensityLevel = state.intensityLevel,
                        outcome = state.outcome
                        )) },
                    modifier = Modifier
                        .padding(top = 24.dp),
                    enabled = !state.isSavingLog
                ) {
                    if (state.logId != null) {
                        Text(text = stringResource(R.string.add_log_update))
                    } else {
                        Text(text = stringResource(R.string.add_log_save))
                    }
                }
            }
        }
    }
}

private val previewLogState = AddEditLogState(
    logId = null,
    intensityLevel = 5,
    outcome = true,
    isSavingLog = true,
)

private val previewLogStateEmpty = AddEditLogState()

@PreviewLightDark
@Composable
private fun AddEditLogScreenPreview() {
    RecoveryCompanionTheme {
        AddEditLogScreen(
            state = previewLogStateEmpty,
            onAction = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
        )
    }
}

@PreviewLightDark
@Composable
private fun AddEditLogDialogPreview() {
    RecoveryCompanionTheme {
        AddEditLogScreen(
            state = AddEditLogState(showDeleteDialog = true),
            onAction = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}