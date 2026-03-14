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
                startDestination = "log_list",
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
                composable("log_list") {
                    LogListScreenRoot(
                        onAddLogClick = { navController.navigate("add_log") },
                        onLogClick = { logId ->
                            navController.navigate("add_log/$logId")
                        }
                    )
                }

                //Detail Edit
                composable(
                    route = "add_log/{logId}",
                    arguments = listOf(
                        navArgument("logId") {
                            type = NavType.StringType
                            defaultValue = "0"
                        }
                    )
                ) {
                    AddEditLogScreenRoot(onBack = { navController.navigateUp() })
                }

                //Detail Add
                composable("add_log") {
                    AddEditLogScreenRoot(onBack = { navController.navigateUp() })
                }
            }
        }
    }
}
