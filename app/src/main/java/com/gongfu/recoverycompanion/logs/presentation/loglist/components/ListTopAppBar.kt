package com.gongfu.recoverycompanion.logs.presentation.loglist.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.gongfu.recoverycompanion.R
import com.gongfu.recoverycompanion.ui.theme.LogoIcon
import com.gongfu.recoverycompanion.ui.theme.Poppins
import com.gongfu.recoverycompanion.ui.theme.RecoveryCompanionTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    menuItems: List<DropDownItem> = emptyList(),
    filterItems: List<DropDownItem> = emptyList(),
    onFilterItemClick: (Int) -> Unit = {},
    onMenuItemClick: (Int) -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
) {
    var isFilterDropDownOpen by rememberSaveable {
        mutableStateOf(false)
    }
    var isDropDownMenuOpen by rememberSaveable {
        mutableStateOf(false)
    }
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
        ),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = LogoIcon,
                    contentDescription = "App Logo",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontFamily = Poppins
                )
            }
        },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        actions = {

            if (filterItems.isNotEmpty()) {
                Box {
                    DropdownMenu(
                        expanded = isFilterDropDownOpen,
                        onDismissRequest = {
                            isFilterDropDownOpen = false
                        }
                    ) {
                        filterItems.forEachIndexed { index, item ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .clickable(onClick = { onFilterItemClick(index) })
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.title
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = item.title
                                )
                            }
                        }
                    }
                    IconButton(onClick = {
                        isFilterDropDownOpen = true
                    }) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = stringResource(R.string.filter_list),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            if (menuItems.isNotEmpty()) {
                Box {
                   DropdownMenu(
                       expanded = isDropDownMenuOpen,
                       onDismissRequest = {
                           isDropDownMenuOpen = false
                       }
                   ) {
                       menuItems.forEachIndexed { index, item ->
                           Row(
                               verticalAlignment = Alignment.CenterVertically,
                               modifier = Modifier
                                   .clickable(onClick = { onMenuItemClick(index) })
                                   .fillMaxWidth()
                                   .padding(16.dp)
                           ) {
                               Icon(
                                   imageVector = item.icon,
                                   contentDescription = item.title
                               )
                               Spacer(modifier = Modifier.width(8.dp))
                               Text(
                                   text = item.title
                               )
                           }
                       }
                   }
                   IconButton(onClick = {
                       isDropDownMenuOpen = true
                   }) {
                       Icon(
                           imageVector = Icons.Default.MoreVert,
                           contentDescription = stringResource(R.string.more_menu),
                           tint = MaterialTheme.colorScheme.onSurfaceVariant
                       )
                   }
               }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Composable
private fun ListTopAppBarPreview() {
    RecoveryCompanionTheme {
        ListTopAppBar(
            title = "Log Entries",
            modifier = Modifier.fillMaxWidth(),
            menuItems = listOf(
                DropDownItem(
                    icon = Icons.Default.Settings,
                    title = "Settings"
                ),
                DropDownItem(
                    icon = Icons.Default.Delete,
                    title = "Delete"
                )
            ),
            filterItems = listOf(
                DropDownItem(
                    icon = Icons.Default.ArrowUpward,
                    title = "Date Ascending"
                ),
                DropDownItem(
                    icon = Icons.Default.ArrowDownward,
                    title = "Date Descending"
                ),
                DropDownItem(
                    icon = Icons.Default.ArrowUpward,
                    title = "Intensity Ascending"
                ),
                DropDownItem(
                    icon = Icons.Default.ArrowDownward,
                    title = "Intensity Descending"
                ),
            ),
            onFilterItemClick = {},
            onMenuItemClick = {},
        )
    }
}