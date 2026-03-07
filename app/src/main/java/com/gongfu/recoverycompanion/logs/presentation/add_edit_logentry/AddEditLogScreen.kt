package com.gongfu.recoverycompanion.logs.presentation.add_edit_logentry

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gongfu.recoverycompanion.core.presentation.designsystem.components.RecoveryTopAppBar
import com.gongfu.recoverycompanion.logs.presentation.add_edit_logentry.components.LogEntryTextField
import com.gongfu.recoverycompanion.ui.theme.RecoveryCompanionTheme
import com.gongfu.recoverycompanion.ui.theme.Typography
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddEditLogScreenRoot(
    onBack: () -> Unit,
    viewModel: AddEditLogViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    AddEditLogScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is AddEditLogAction.OnBackClick -> onBack()
                is AddEditLogAction.OnSaveClick -> Unit
            }
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
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
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            RecoveryTopAppBar(
                showBackButton = true,
                //placeholder
                title = "New Log Entry!",
                scrollBehavior = scrollBehavior,
                onBackClick = { onAction(AddEditLogAction.OnBackClick) }
            )
        }
    ) { innerPadding ->
        LazyColumn(  // Scrollable form
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                // Title
                LogEntryTextField(
                    label = "Title",
                    state = state.title,
                    error = null,
                )
                // Description
                LogEntryTextField(
                    label = "Description",
                    state = state.description,
                    lineLimits = TextFieldLineLimits.MultiLine(
                        minHeightInLines = 3,
                        maxHeightInLines = 3
                    ),
                    error = null
                )

                // Trigger
                LogEntryTextField(
                    label = "Trigger",
                    state = state.trigger,
                    error = null,
                )

                // Location
                LogEntryTextField(
                    label = "Location",
                    state = state.location,
                    error = null,
                )

                // Intensity Level (Slider or picker - placeholder)
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .padding(bottom = 16.dp)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(vertical = 10.dp, horizontal = 5.dp)
                ) {
                    Text(
                        text = "Intensity Level",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = Typography.labelMedium,
                        modifier = Modifier.padding(start = 5.dp)

                    )
                    Slider(
                        state = SliderState(
                            value = state.intensityLevel.toFloat(),
                            valueRange = 1f..10f,
                            steps = 8
                        ),
                        modifier = Modifier.padding(horizontal = 5.dp)
                    )

                   // Steps labels
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .padding(start = 19.dp, end = 13.dp)
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

                // Body Response
                LogEntryTextField(
                    label = "Body Response",
                    state = state.bodyResponse,
                    error = null,
                )

                // Outcome Toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Healthy Response?",
                        fontStyle = FontStyle.Italic
                    )
                    Switch(
                        checked = state.outcome,
                        onCheckedChange = null,
                    )
                }
                // Save Button
                Button(
                    onClick = { onAction(AddEditLogAction.OnSaveClick) },
                    modifier = Modifier
                        .padding(top = 24.dp),
                    enabled = !state.isSavingLog
                ) {
                    Text("Save Log")
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun AddEditLogScreenPreview() {
    RecoveryCompanionTheme {
        AddEditLogScreen(
            state = AddEditLogState(
                title = TextFieldState(initialText = "Morning routine win"),
                description = TextFieldState(initialText = "Woke up on time, meditated, exercised"),
                trigger = TextFieldState(initialText = "Good sleep"),
                location = TextFieldState(initialText = "Home"),
                intensityLevel = 3,
                bodyResponse = TextFieldState(initialText = "Energized, clear-headed"),
                outcome = true,
                isSavingLog = false
            ),
            onAction = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}
