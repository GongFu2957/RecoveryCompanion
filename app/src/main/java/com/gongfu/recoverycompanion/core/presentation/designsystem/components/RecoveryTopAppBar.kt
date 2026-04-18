package com.gongfu.recoverycompanion.core.presentation.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.gongfu.recoverycompanion.R
import com.gongfu.recoverycompanion.logs.presentation.loglist.components.DropDownItem
import com.gongfu.recoverycompanion.ui.theme.Poppins
import com.gongfu.recoverycompanion.ui.theme.RecoveryCompanionTheme
import com.gongfu.recoverycompanion.ui.theme.backArrowAlt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecoveryTopAppBar(
    showBackButton: Boolean,
    modifier: Modifier = Modifier,
    showFilterIcon: Boolean = false,
    title: String,
    menuItems: List<DropDownItem> = emptyList(),
    onMenuItemClick: (Int) -> Unit = {},
    onFilterClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    startContent: (@Composable () -> Unit)? = null
) {
    var isDropDownMenuOpen by rememberSaveable {
        mutableStateOf(false)
    }
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
        ),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.heightIn(min = 64.dp, max = 96.dp)
            ) {
                startContent?.invoke()
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontFamily = Poppins,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = backArrowAlt,
                        contentDescription = stringResource(R.string.go_back),
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        actions = {
            if (showFilterIcon) {
                IconButton(
                    onClick = onFilterClick
                ) {
                    Icon(
                        imageVector = Icons.Default.FilterList,
                        contentDescription = stringResource(R.string.filter_list),
                    )
                }
            }

            if (menuItems.isNotEmpty()) {
                Box {
                   DropdownMenu(
                       expanded = isDropDownMenuOpen,
                       onDismissRequest = {
                           isDropDownMenuOpen = false
                       },
                       shape = RoundedCornerShape(12.dp),
                       containerColor = MaterialTheme.colorScheme.surfaceContainer,
                       tonalElevation = 2.dp,
                       shadowElevation = 4.dp
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
private fun RecoveryTopAppBarPreview() {
    RecoveryCompanionTheme {
        RecoveryTopAppBar(
            showBackButton = true,
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
            showFilterIcon = true,
            onFilterClick = {},
            onMenuItemClick = {},
        )
    }
}