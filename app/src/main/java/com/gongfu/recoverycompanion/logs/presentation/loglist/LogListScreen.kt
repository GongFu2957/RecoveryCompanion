package com.gongfu.recoverycompanion.logs.presentation.loglist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.gongfu.recoverycompanion.logs.presentation.loglist.components.LogEntryItem
import com.gongfu.recoverycompanion.logs.presentation.loglist.components.previewLog
import com.gongfu.recoverycompanion.ui.theme.RecoveryCompanionTheme

@Composable
fun LogEntryScreen(
    state: LogListState,
    onAction: (LogListAction) -> Unit,
    modifier: Modifier = Modifier
) {
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

@PreviewLightDark
@Composable
private fun LogEntryScreenPreview() {
    RecoveryCompanionTheme {
        LogEntryScreen(
            state = LogListState(
                logs = (1..50).map {
                    previewLog.copy(
                        title = "$it Home Alone."
                    )
                }
            ),
            onAction = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}