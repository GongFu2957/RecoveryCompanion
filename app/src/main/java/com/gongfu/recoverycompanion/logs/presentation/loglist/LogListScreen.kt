package com.gongfu.recoverycompanion.logs.presentation.loglist

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gongfu.recoverycompanion.R
import com.gongfu.recoverycompanion.core.presentation.designsystem.components.FilterChips
import com.gongfu.recoverycompanion.core.presentation.designsystem.components.RecoveryTopAppBar
import com.gongfu.recoverycompanion.core.tmp.previewLogList
import com.gongfu.recoverycompanion.logs.domain.util.FilterOption
import com.gongfu.recoverycompanion.logs.domain.util.LogOrder
import com.gongfu.recoverycompanion.logs.domain.util.OrderType
import com.gongfu.recoverycompanion.logs.presentation.loglist.components.DropDownItem
import com.gongfu.recoverycompanion.logs.presentation.loglist.components.FilterItem
import com.gongfu.recoverycompanion.logs.presentation.loglist.components.LogEntryItem
import com.gongfu.recoverycompanion.ui.theme.LogoIcon
import com.gongfu.recoverycompanion.ui.theme.Poppins
import com.gongfu.recoverycompanion.ui.theme.RecoveryCompanionTheme
import org.koin.androidx.compose.koinViewModel


@Composable
fun LogListScreenRoot(
    onAddLogClick: () -> Unit,
    onLogClick: (Long) -> Unit,
    viewModel: LogListViewModel = koinViewModel(),
    ) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val testState = LogListState(
        logs = previewLogList
    )
    val context = LocalContext.current
    LaunchedEffect(viewModel.events) {
        viewModel.events.collect { event ->
            when (event) {
                is LogListEvent.Error -> {
                    Toast.makeText(
                        context,
                        event.error,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    LogListScreen(
        state = state,
        onAction = { action ->
            viewModel.onAction(action)
            when (action) {
                is LogListAction.CreateLog -> onAddLogClick()
                is LogListAction.OnLogClick -> onLogClick(action.logId)
                else -> {}
            }
        }
    )
}
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
    val filterOptions = listOf(
        FilterOption(
            icon = Icons.Default.ArrowDownward,
            title = "Date",
            order = LogOrder.Date(orderType = OrderType.Descending)
        ),
        FilterOption(
            icon = Icons.Default.ArrowUpward,
            title = "Date",
            order = LogOrder.Date(orderType = OrderType.Ascending)
        ),
        FilterOption(
            icon = Icons.Default.ArrowDownward,
            title = "Intensity Level",
            order = LogOrder.IntensityLevel(orderType = OrderType.Descending)
        ),
        FilterOption(
            icon = Icons.Default.ArrowUpward,
            title = "Intensity Level",
            order = LogOrder.IntensityLevel(orderType = OrderType.Ascending)
        ),
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            RecoveryTopAppBar(
                showBackButton = false,
                showFilterIcon = state.logs.isNotEmpty(),
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
                onFilterClick = { onAction(LogListAction.OnFilterClick) },
                startContent = {
                    Icon(
                        imageVector = LogoIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(30.dp)
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAction(LogListAction.CreateLog) },
                containerColor = MaterialTheme.colorScheme.primary,
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
        Column(Modifier.padding(innerPadding)) {
            if (state.isFilterOpen && state.logs.isNotEmpty()) {
                val filterItems = filterOptions.map { option ->
                    FilterItem(
                        icon = option.icon,
                        title = option.title,
                        isSelected = when (val current = state.logOrder) {
                            is LogOrder.Date -> option.order is LogOrder.Date &&
                                    current.orderType == option.order.orderType
                            is LogOrder.IntensityLevel -> option.order is LogOrder.IntensityLevel &&
                                    current.orderType == option.order.orderType
                        }
                    )
                }
                FilterChips(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    filterItems = filterItems,
                    onFilterItemClick = { index ->
                        val selectedOption = filterOptions[index]
                        onAction(LogListAction.Order(selectedOption.order))
                    }
                )
            }
            if (state.logs.isEmpty()) {
                LogListEmptyContent()
            } else {
                LogListContent(
                    state = state,
                    onAction = onAction
                )
            }
        }
    }
}

@Composable
private fun LogListContent(
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

@Composable
private fun LogListEmptyContent(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.no_logs),
            fontFamily = Poppins,
        )
    }
}

@PreviewLightDark
@Composable
private fun LogListScreenPreview() {
    RecoveryCompanionTheme {
        LogListScreen(
            state = LogListState(
                logs = previewLogList,
                isFilterOpen = false
            ),
            onAction = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}

@PreviewLightDark
@Composable
private fun LogListScreenEmptyContentPreview() {
    RecoveryCompanionTheme {
        LogListScreen(
            state = LogListState(
                logs = emptyList()
            ),
            onAction = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}

@PreviewLightDark
@Composable
private fun LogListEmptyContentPreview() {
    RecoveryCompanionTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            LogListEmptyContent()
        }
    }
}