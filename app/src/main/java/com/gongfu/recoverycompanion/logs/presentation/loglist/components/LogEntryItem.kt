package com.gongfu.recoverycompanion.logs.presentation.loglist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gongfu.recoverycompanion.R
import com.gongfu.recoverycompanion.logs.domain.model.LogEntry
import com.gongfu.recoverycompanion.logs.domain.model.OutcomeType
import com.gongfu.recoverycompanion.logs.presentation.utils.formatEpochMillis
import com.gongfu.recoverycompanion.ui.theme.RecoveryCompanionTheme
import com.gongfu.recoverycompanion.ui.theme.neutralLog
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

    val (logImage, logColor) = when (log.outcome) {
        OutcomeType.SUCCESS -> successLog to Color(120,0,0)
        OutcomeType.NEUTRAL -> neutralLog to Color(120,120,120)
        OutcomeType.SLIP -> slipLog to Color(0,120,0)
    }

    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp)
            .heightIn(min = 64.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)

    ) {
        Icon(
            imageVector = logImage,
            contentDescription = "Icon",
            tint = logColor,
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
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = log.title,
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        Text(
                            text = stringResource(R.string.intensity_level),
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            lineHeight = 14.sp,
                            color = contentColor.copy(alpha = .7f),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            modifier = Modifier.weight(1f)
                            )
                        Text(
                            text = formatEpochMillis(log.timestamp),
                            fontWeight = FontWeight.Light,
                            fontStyle = FontStyle.Italic,
                            fontSize = 11.sp,
                            lineHeight = 14.sp,
                            color = contentColor.copy(alpha = .6f),
                            maxLines = 1,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.End
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
                id = 0, timestamp = timeStamp, title = "Woke up late again, I can't be",
                description = "Snoozed alarm 5 times and missed my morning routine. Felt defeated before the day even started.",
                trigger = "Oversleeping", location = "Bedroom", intensityLevel = 7,
                bodyResponse = "Heavy fatigue, foggy brain, slight nausea", outcome = OutcomeType.SLIP
            ),
            onClick = { /* TO DO */ },
            modifier = Modifier.background(
                MaterialTheme.colorScheme.background
            )
        )
    }
}

internal val timeStamp = ZonedDateTime.now().toInstant().toEpochMilli()
