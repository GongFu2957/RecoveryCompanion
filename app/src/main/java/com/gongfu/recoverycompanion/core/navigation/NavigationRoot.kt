package com.gongfu.recoverycompanion.core.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gongfu.recoverycompanion.logs.presentation.add_edit_logentry.AddEditLogScreenRoot
import com.gongfu.recoverycompanion.logs.presentation.loglist.LogListScreenRoot
import com.gongfu.recoverycompanion.logs.presentation.utils.LogDestinations
import com.gongfu.recoverycompanion.ui.theme.RecoveryCompanionTheme

@Composable
fun NavigationRoot() {
    val navController = rememberNavController()

    RecoveryCompanionTheme {
        Surface {
            NavHost(
                navController = navController,
                startDestination = LogDestinations.LogList.route,
                modifier = Modifier.fillMaxSize(),
            ) {
                //Main LogListScreen
                composable(LogDestinations.LogList.route) {
                    LogListScreenRoot(
                        onAddLogClick = { navController.navigate(LogDestinations.AddLog.route) }
                    )
                }

                //AddLogScreen
                composable(LogDestinations.AddLog.route) {
                    AddEditLogScreenRoot(
                        onBack = { navController.navigateUp() }
                    )
                }

                //Implement LogDetailScreen
            }
        }
    }
}

