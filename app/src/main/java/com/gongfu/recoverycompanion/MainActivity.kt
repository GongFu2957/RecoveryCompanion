package com.gongfu.recoverycompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.gongfu.recoverycompanion.logs.presentation.logentry.LogEntryScreen
import com.gongfu.recoverycompanion.logs.presentation.logentry.LogEntryState
import com.gongfu.recoverycompanion.ui.theme.RecoveryCompanionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecoveryCompanionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val state = LogEntryState()
                    LogEntryScreen(
                        state = state,
                        onAction = {},
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
