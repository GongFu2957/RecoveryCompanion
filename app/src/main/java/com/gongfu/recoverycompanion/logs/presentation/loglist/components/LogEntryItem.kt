package com.gongfu.recoverycompanion.logs.presentation.loglist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gongfu.recoverycompanion.R
import com.gongfu.recoverycompanion.logs.domain.model.LogEntry
import com.gongfu.recoverycompanion.logs.presentation.utils.formatEpochMillis
import com.gongfu.recoverycompanion.ui.theme.RecoveryCompanionTheme
import com.gongfu.recoverycompanion.ui.theme.slipLog
import com.gongfu.recoverycompanion.ui.theme.successLog
import java.time.ZonedDateTime

@Composable
fun LogEntryItem(
    log: LogEntry,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val contentColor = if(isSystemInDarkTheme()) Color.White else Color.Black

    /* temp until input is handled in the add/edit log screen */
    val maxTitleChars = 30
    val truncatedTitle = remember(log.title) {
        if (log.title.length <= maxTitleChars) {
            log.title
        } else {
            "${log.title.take(maxTitleChars)}..."
        }
    }
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)

    ) {
        Icon(
            imageVector = if (!log.outcome) slipLog else successLog,
            contentDescription = "Icon",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(70.dp)
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(
                        text = truncatedTitle,
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(R.string.intensity_level),
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            lineHeight = 14.sp,
                            color = contentColor.copy(alpha = .7f),
                            )
                        Text(
                            text = formatEpochMillis(log.timestamp),
                            fontWeight = FontWeight.Light,
                            fontStyle = FontStyle.Italic,
                            fontSize = 11.sp,
                            lineHeight = 13.sp,
                            color = contentColor.copy(alpha = .6f)
                        )
                    }
                }
            }
            ProgressLine(
                value = log.intensityLevel.toFloat(),
                modifier = Modifier.fillMaxWidth(),
                contentColor = contentColor
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun LogEntryItemPreview() {
    RecoveryCompanionTheme {
        LogEntryItem(
            log = LogEntry(
                id = 0, timestamp = timeStamp, title = "Woke up late again, I can'tbe",
                description = "Snoozed alarm 5 times and missed my morning routine. Felt defeated before the day even started.",
                trigger = "Oversleeping", location = "Bedroom", intensityLevel = 7,
                bodyResponse = "Heavy fatigue, foggy brain, slight nausea", outcome = false
            ),
            onClick = { /* TO DO */ },
            modifier = Modifier.background(
                MaterialTheme.colorScheme.background
            )
        )
    }
}

internal val timeStamp = ZonedDateTime.now().toInstant().toEpochMilli()
