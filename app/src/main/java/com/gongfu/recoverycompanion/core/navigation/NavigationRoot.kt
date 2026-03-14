package com.gongfu.recoverycompanion.core.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gongfu.recoverycompanion.logs.presentation.add_edit_logentry.AddEditLogScreenRoot
import com.gongfu.recoverycompanion.logs.presentation.loglist.LogListScreenRoot
import com.gongfu.recoverycompanion.logs.presentation.utils.LogDestinations
import com.gongfu.recoverycompanion.ui.theme.RecoveryCompanionTheme

@Composable
fun NavigationRoot() {
    val navController = rememberNavController()

    // Animation setup with RTL support
    val isLtr = LocalLayoutDirection.current == LayoutDirection.Ltr
    val slideTime = 300

    RecoveryCompanionTheme {
        Surface {
            NavHost(
                navController = navController,
                startDestination = LogDestinations.LogList.route,
                modifier = Modifier.fillMaxSize(),
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { if (isLtr) it else -it },
                        animationSpec = tween(slideTime)
                    ) + fadeIn(animationSpec = tween(slideTime))
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { if (isLtr) -it else it },
                        animationSpec = tween(slideTime)
                    ) + fadeOut(animationSpec = tween(slideTime))
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { if (isLtr) -it else it },
                        animationSpec = tween(slideTime)
                    ) + fadeIn(animationSpec = tween(slideTime))
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { if (isLtr) it else -it },
                        animationSpec = tween(slideTime)
                    ) + fadeOut(animationSpec = tween(slideTime))
                }
            ) {
                //Main LogListScreen
                composable(LogDestinations.LogList.route) {
                    LogListScreenRoot(
                        onAddLogClick = { navController.navigate(LogDestinations.AddLog.route) },
                        onLogClick = { logId ->
                            navController.navigate(LogDestinations.AddLog.createRoute(logId))}
                    )
                }

                //AddLogScreen
                composable(
                    LogDestinations.AddLog.route,
                    arguments = listOf(
                        navArgument("logId") {
                            type = NavType.StringType
                            nullable = true
                            defaultValue = null
                        }
                    )
                ) { backStackEntry ->
                    AddEditLogScreenRoot(  // Remove logId extraction here
                        onBack = { navController.navigateUp() }
                    )
                }

            }
        }
    }
}
