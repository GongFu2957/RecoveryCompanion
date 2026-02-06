package com.gongfu.recoverycompanion.logs.presentation.logentry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gongfu.recoverycompanion.di.SimpleLogger
import com.gongfu.recoverycompanion.ui.theme.RecoveryCompanionTheme
import org.koin.compose.koinInject

@Composable
fun LogEntryScreen(modifier: Modifier = Modifier) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val logger: SimpleLogger = koinInject()
        LaunchedEffect(Unit) {
            logger.log("LogEntryScreen was created!")
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
            .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "New Log Entry Holder",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

@Preview
@Composable
fun LogEntryScreenPreview() {
    RecoveryCompanionTheme {
        LogEntryScreen()
    }
}