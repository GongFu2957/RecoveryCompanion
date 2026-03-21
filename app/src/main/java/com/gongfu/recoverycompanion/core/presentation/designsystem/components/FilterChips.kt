package com.gongfu.recoverycompanion.core.presentation.designsystem.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.gongfu.recoverycompanion.logs.presentation.loglist.components.FilterItem
import com.gongfu.recoverycompanion.ui.theme.RecoveryCompanionTheme

@Composable
fun FilterChips(
    modifier: Modifier = Modifier,
    filterItems: List<FilterItem> = emptyList(),
    onFilterItemClick: (Int) -> Unit,
) {
    val scrollState = rememberScrollState()

    if (filterItems.isNotEmpty()) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            filterItems.forEachIndexed { index, item ->
                FilterChip(
                    selected = item.isSelected,
                    onClick = { onFilterItemClick(index) },
                    label = {
                        Text(
                            text = item.title,
                            maxLines = 1,
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title
                        )
                    },
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewFilterChips() {
    RecoveryCompanionTheme {
        FilterChips(
            filterItems = listOf<FilterItem>(
                FilterItem(
                    icon = Icons.Default.ArrowUpward ,
                    title = "Date",
                    isSelected = false
                ),
                FilterItem(
                    icon = Icons.Default.ArrowDownward,
                    title = "Date",
                    isSelected = true
                ),
                FilterItem(
                    icon = Icons.Default.ArrowUpward ,
                    title = "Intensity Level",
                    isSelected = false
                ),
                FilterItem(
                    icon = Icons.Default.ArrowDownward,
                    title = "Intensity Level",
                    isSelected = false
                ),
                FilterItem(
                    icon = Icons.Default.ArrowUpward ,
                    title = "Date",
                    isSelected = false
                ),
                FilterItem(
                    icon = Icons.Default.ArrowDownward,
                    title = "Date",
                    isSelected = true
                ),
                FilterItem(
                    icon = Icons.Default.ArrowUpward ,
                    title = "Intensity Level",
                    isSelected = false
                ),
                FilterItem(
                    icon = Icons.Default.ArrowDownward,
                    title = "Intensity Level",
                    isSelected = false
                ),
            ),
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) { }
    }
}
