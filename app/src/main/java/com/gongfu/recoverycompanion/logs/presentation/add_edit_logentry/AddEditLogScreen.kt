package com.gongfu.recoverycompanion.logs.presentation.add_edit_logentry

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gongfu.recoverycompanion.R
import com.gongfu.recoverycompanion.core.presentation.designsystem.components.RecoveryTopAppBar
import com.gongfu.recoverycompanion.logs.presentation.add_edit_logentry.components.LogEntryTextField
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
    val maxTitleCharCount = 27
    val titleLength = state.title.text.length
    val truncatedTitle = if (titleLength <= maxTitleCharCount) {
        "${state.title.text}"
    } else {
        "${state.title.text.take(maxTitleCharCount)}..."
    }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            RecoveryTopAppBar(
                showBackButton = true,
                //placeholder
                title = if (state.logId == null) {
                    stringResource(R.string.new_log_entry)
                } else truncatedTitle,
                scrollBehavior = scrollBehavior,
                onBackClick = { onAction(AddEditLogAction.OnBackClick) }
            )
        },
        contentWindowInsets = WindowInsets.safeDrawing
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
                    label = stringResource(R.string.add_log_title),
                    state = state.title,
                )
                // Description
                LogEntryTextField(
                    label = stringResource(R.string.add_log_description),
                    state = state.description,
                    lineLimits = TextFieldLineLimits.MultiLine(
                        minHeightInLines = 4,
                        maxHeightInLines = 4
                    ),
                    endIcon = helpQuestion,
                    helpText = "This is where you put all the details related to your Log. Whether that be the steps leading up to it, thought patterns, or your feelings about it afterwards. Being more detailed will help you find patterns easier.",
                )

                // Trigger
                LogEntryTextField(
                    label = stringResource(R.string.add_log_trigger),
                    state = state.trigger,
                    endIcon = helpQuestion,
                    helpText = "A trigger is a cue that primes your mind and body to respond in a specific way."
                )

                // Location
                LogEntryTextField(
                    label = stringResource(R.string.add_log_location),
                    state = state.location,
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
                            text = stringResource(R.string.add_log_intensitylevel),
                            color = MaterialTheme.colorScheme.onBackground,
                            style = Typography.labelMedium,
                        )
                        Slider(
                            state = SliderState(
                                value = state.intensityLevel.toFloat(),
                                valueRange = 1f..10f,
                                steps = 8
                            ),
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
                    state = state.bodyResponse,
                    endIcon = helpQuestion,
                    helpText = ""
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
                    Text(
                        text = stringResource(R.string.add_log_save)
                    )
                }
            }
        }
    }
}

private val previewLogState = AddEditLogState(
    logId = 11221,
    title = TextFieldState(initialText = "Morning routine win. GOD IS ON THE MOVE WOOHOO"),
    description = TextFieldState(initialText = "Woke up on time, meditated, exercised. Honestly was feeling " +
            "really good and really well rested. I prayed for 45 million seconds and just let God do his wonderful work in my heart."),
    trigger = TextFieldState(initialText = "Good sleep"),
    location = TextFieldState(initialText = "Home"),
    intensityLevel = 3,
    bodyResponse = TextFieldState(initialText = "Energized, clear-headed"),
    outcome = true,
    isSavingLog = false
)

private val previewLogStateEmpty = AddEditLogState()

@PreviewLightDark
@Composable
private fun AddEditLogScreenPreview() {
    RecoveryCompanionTheme {
        AddEditLogScreen(
            state = previewLogStateEmpty,
            onAction = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}
