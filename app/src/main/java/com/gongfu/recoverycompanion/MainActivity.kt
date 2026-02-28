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
import com.gongfu.recoverycompanion.logs.presentation.loglist.LogEntryScreen
import com.gongfu.recoverycompanion.logs.presentation.loglist.LogListState
import com.gongfu.recoverycompanion.logs.presentation.loglist.components.previewLog
import com.gongfu.recoverycompanion.ui.theme.RecoveryCompanionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecoveryCompanionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LogEntryScreen(
                        state = previewLogListState,
                        onAction =  { },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
private val previewLogListState =
    LogListState(
            logs = (1..50).map {
                previewLog.copy(
                    title = "$it Home Alone."
                )
            }
    )
