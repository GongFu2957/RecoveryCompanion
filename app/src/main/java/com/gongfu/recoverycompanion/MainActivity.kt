package com.gongfu.recoverycompanion

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.gongfu.recoverycompanion.logs.presentation.logentry.LogEntryScreen
import com.gongfu.recoverycompanion.logs.presentation.logentry.LogEntryState
import com.gongfu.recoverycompanion.logs.presentation.logentry.components.previewLog
import com.gongfu.recoverycompanion.ui.theme.RecoveryCompanionTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecoveryCompanionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LogEntryScreen(
                        state = previewLogEntryState,
                        onAction = {},
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
private val previewLogEntryState =
    LogEntryState(
            logs = (1..50).map {
                previewLog.copy(
                    title = "$it Home Alone."
                )
            }
    )
