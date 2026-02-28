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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gongfu.recoverycompanion.logs.domain.model.LogEntry
import com.gongfu.recoverycompanion.logs.presentation.utils.formatEpochMillis
import com.gongfu.recoverycompanion.ui.theme.RecoveryCompanionTheme
import java.time.ZonedDateTime

@Composable
fun LogEntryItem(
    log: LogEntry,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val contentColor = if(isSystemInDarkTheme()) Color.White else Color.Black
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)

    ) {
        Icon(
            imageVector = Icons.Default.ThumbUp,
            contentDescription = "Icon",
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.size(50.dp)
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = log.title,
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        color = contentColor,
                    )
                    Text(
                        text = "Intensity Level",
                        style = TextStyle(
                            fontWeight = FontWeight.Light,
                            fontSize = 8.sp,
                            color = contentColor,
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = true
                            )
                        )
                    )
                }
                Text(
                    text = formatEpochMillis(log.timestamp),
                    fontWeight = FontWeight.Light,
                    fontStyle = FontStyle.Italic,
                    fontSize = 9.sp,
                    color = contentColor
                )
            }
            ProgressLine(
                value = log.intensityLevel.toFloat(),
                modifier = Modifier.fillMaxWidth(),
                contentColor = contentColor
            )
        }
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "Open menu",
            tint = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier
                .clickable(onClick = onClick)
                .size(25.dp)
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.secondaryContainer)
        )
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

internal val timeStamp = ZonedDateTime.now().toInstant().toEpochMilli()
internal val previewLog = LogEntry(
        id = 0,
        timestamp = timeStamp,
        title = "Title",
        description = """I had a lot of extra time to work on my stuff today. But I didn't use my time like I should have. I was going to really try a lot harder this time, but it just got the best of me. I didn't know what to do in the moment.""".trimMargin(),
        trigger = "Procrastination",
        location = "Bathroom",
        intensityLevel = 7,
        bodyResponse = "Felt a sense of uneasiness and a little bit of shaking anxiety."
)

