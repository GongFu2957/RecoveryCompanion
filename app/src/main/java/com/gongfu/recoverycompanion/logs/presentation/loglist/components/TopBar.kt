package com.gongfu.recoverycompanion.logs.presentation.loglist.components

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.gongfu.recoverycompanion.R
import com.gongfu.recoverycompanion.ui.theme.RecoveryCompanionTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    onFilterClick: () -> Unit,
    onMoreMenuClick: () -> Unit,
    modifier: Modifier
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        ),
        title = {
            Text(
                text = "Log Entries",
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        },
        actions = {
            IconButton(onClick = onFilterClick) {
                Icon(
                    imageVector = Icons.Filled.FilterList,
                    contentDescription = stringResource(R.string.filter_list)
                )
            }

            IconButton(onClick = onMoreMenuClick) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = stringResource(R.string.more_menu)
                )
            }
        },
    )
}

@PreviewLightDark
@Composable
private fun TopBarPreview() {
    RecoveryCompanionTheme {
        TopBar(
            onFilterClick = {},
            onMoreMenuClick = {},
            modifier = Modifier.background(
                MaterialTheme.colorScheme.background
            )
        )
    }
}