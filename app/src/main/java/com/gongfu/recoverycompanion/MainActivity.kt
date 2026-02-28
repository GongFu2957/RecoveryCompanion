package com.gongfu.recoverycompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.gongfu.recoverycompanion.logs.presentation.loglist.LogListScreen
import com.gongfu.recoverycompanion.logs.presentation.loglist.LogListState
import com.gongfu.recoverycompanion.logs.presentation.loglist.components.previewLog
import com.gongfu.recoverycompanion.logs.presentation.loglist.components.previewLog2
import com.gongfu.recoverycompanion.logs.presentation.loglist.components.previewLog3
import com.gongfu.recoverycompanion.logs.presentation.loglist.components.previewLog4
import com.gongfu.recoverycompanion.ui.theme.RecoveryCompanionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecoveryCompanionTheme {
                    LogListScreen(
                        state = previewLogListState,
                        onAction =  { },
                        modifier = Modifier.fillMaxSize()
                    )
            }
        }
    }
}


private val previewLogs = listOf(
   previewLog,
    previewLog2,
    previewLog3,
    previewLog4
)
private val previewLogListState =
    LogListState(
        logs = previewLogs.shuffled().flatMap { baseLog ->
            (1..13).map { // ~50 total items
                baseLog.copy(
                    title = baseLog.title + " $it",
                    id = it.toLong()
                )
            }
        }.shuffled() // Final shuffle for variety
    )
