package com.gongfu.recoverycompanion.logs.presentation.loglist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gongfu.recoverycompanion.R
import com.gongfu.recoverycompanion.logs.domain.model.LogEntry
import com.gongfu.recoverycompanion.logs.presentation.add_edit_logentry.components.LogEntryTextField
import com.gongfu.recoverycompanion.logs.presentation.utils.formatEpochMillis
import com.gongfu.recoverycompanion.ui.theme.RecoveryCompanionTheme

@Composable
fun LogEntryItem(
    log: LogEntry,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val contentColor = if(isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = log.title,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    color = contentColor
                )
                Text(
                    text = formatEpochMillis(log.timestamp),
                    fontWeight = FontWeight.Light,
                    fontSize = 14.sp,
                    color = contentColor
                )
            }
        }
        Column() {
            Text(
                text = stringResource(R.string.description_field),
                fontWeight = FontWeight.Thin,
                fontStyle = FontStyle.Italic,
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = log.description,
                fontWeight = FontWeight.Light,
                fontSize = 14.sp,
                color = contentColor
            )
        }
    }

}


@PreviewLightDark
@Composable
private fun LogEntryItemPreview() {
    RecoveryCompanionTheme {
        LogEntryItem(
            log = previewLog,
            onClick = { /* TO DO */ },
            modifier = Modifier.background(
                MaterialTheme.colorScheme.background
            )
        )
    }
}

internal val previewLog = LogEntry(
        id = 0,
        timestamp = 1772156000000L,
        title = "Home Alone",
        description = "I had a lot of extra time to work on my stuff today. But I didn't use my time like I should have",
        trigger = "Procrastination",
        location = "Bathroom",
        intensityLevel = 7,
        bodyResponse = "Felt a sense of uneasiness and a little bit of shaking anxiety."
)
