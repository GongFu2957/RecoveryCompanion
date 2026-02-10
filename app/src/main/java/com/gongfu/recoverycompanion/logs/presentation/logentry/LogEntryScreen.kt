package com.gongfu.recoverycompanion.logs.presentation.logentry

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gongfu.recoverycompanion.di.SimpleLogger
import com.gongfu.recoverycompanion.logs.presentation.logentry.components.LogEntryItem
import com.gongfu.recoverycompanion.logs.presentation.logentry.components.previewLog
import com.gongfu.recoverycompanion.ui.theme.RecoveryCompanionTheme
import org.koin.compose.koinInject

@Composable
fun LogEntryScreen(
    state: LogEntryState,
    onAction: (LogEntryAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val logger: SimpleLogger = koinInject()
        LaunchedEffect(Unit) {
            logger.log("LogEntryScreen was created!")
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
            .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "New Log Entry Holder",
                style = MaterialTheme.typography.headlineMedium
            )
        }
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.logs) { logEntry ->
                LogEntryItem(
                    log = logEntry,
                    onClick = {},
                    modifier = Modifier.fillMaxWidth()
                )
                HorizontalDivider()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun LogEntryScreenPreview() {
    RecoveryCompanionTheme {
        LogEntryScreen(
            state = LogEntryState(
                logs = (1..50).map {
                    previewLog.copy(title = it.toString())
                }
            ),
            onAction = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}