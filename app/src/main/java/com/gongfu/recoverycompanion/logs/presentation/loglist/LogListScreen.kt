package com.gongfu.recoverycompanion.logs.presentation.loglist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.gongfu.recoverycompanion.logs.presentation.loglist.components.LogEntryItem
import com.gongfu.recoverycompanion.logs.presentation.loglist.components.ListTopAppBar
import com.gongfu.recoverycompanion.logs.presentation.loglist.components.previewLog
import com.gongfu.recoverycompanion.ui.theme.RecoveryCompanionTheme
import com.gongfu.recoverycompanion.R
import com.gongfu.recoverycompanion.logs.presentation.loglist.components.DropDownItem
import com.gongfu.recoverycompanion.logs.presentation.loglist.components.previewLog2
import com.gongfu.recoverycompanion.logs.presentation.loglist.components.previewLog3
import com.gongfu.recoverycompanion.logs.presentation.loglist.components.previewLog4

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogListScreen(
    state: LogListState,
    onAction: (LogListAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = topAppBarState
    )
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            ListTopAppBar(
                title = stringResource(R.string.log_entries),
                scrollBehavior = scrollBehavior,
                menuItems = listOf(
                    DropDownItem(
                        icon = Icons.Default.Settings,
                        title = stringResource(R.string.settings),
                    ),
                    DropDownItem(
                        icon = Icons.Default.Delete,
                        title = stringResource(R.string.delete),
                    )
                ),
                filterItems = listOf(
                    DropDownItem(
                        icon = Icons.Default.ArrowUpward,
                        title = stringResource(R.string.date)
                    ),
                    DropDownItem(
                        icon = Icons.Default.ArrowDownward,
                        title = stringResource(R.string.date)
                    ),
                    DropDownItem(
                        icon = Icons.Default.ArrowUpward,
                        title = stringResource(R.string.intensity_level)
                    ),
                    DropDownItem(
                        icon = Icons.Default.ArrowDownward,
                        title = stringResource(R.string.intensity_level)
                    ),
                ),
                onMenuItemClick = { index ->
                    when (index) {
                        0 -> onAction(LogListAction.OnSettingsClick)
                        1 -> onAction(LogListAction.OnDeleteClick)
                    }
                },
                onFilterItemClick = { index ->
                    when (index) {
                        0 -> onAction(LogListAction.OnSortDateAscending)
                        1 -> onAction(LogListAction.OnSortDateDescending)
                        2 -> onAction(LogListAction.OnSortIntensityLevelAscending)
                        3 -> onAction(LogListAction.OnSortIntensityLevelDescending)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAction(LogListAction.CreateLog) },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.new_task),
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        innerPadding ->
        NoteListContent(
            state = state,
            onAction = onAction,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}

@Composable
private fun NoteListContent(
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
                onClick = { onAction(LogListAction.OnLogClick(logEntry.id))},
                modifier = Modifier.fillMaxWidth()
            )
            HorizontalDivider()
        }
    }

}

@PreviewLightDark
@Composable
private fun LogListScreenPreview() {
    RecoveryCompanionTheme {
        val previewLogs = listOf(
            previewLog,
            previewLog2,
            previewLog3,
            previewLog4
        )
        LogListScreen(
            state = LogListState(
                logs = previewLogs.shuffled().flatMap { baseLog ->
                    (1..13).map { // ~50 total items
                        baseLog.copy(
                            title = baseLog.title + " $it",
                            id = it.toLong()
                        )
                    }
                }.shuffled() // Final shuffle for variety
            ),
            onAction = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}