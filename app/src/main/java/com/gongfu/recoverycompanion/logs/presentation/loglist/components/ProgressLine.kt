package com.gongfu.recoverycompanion.logs.presentation.loglist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gongfu.recoverycompanion.ui.theme.RecoveryCompanionTheme

@Composable
fun ProgressLine(
    value: Float,
    modifier: Modifier = Modifier,
    valueRange: ClosedFloatingPointRange<Float> = 0f..10f,
    strokeThickness: Dp = 10.dp,
    contentColor: Color
) {
    val progressFraction = (value - valueRange.start) / (valueRange.endInclusive - valueRange.start)

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(5.dp)
            .fillMaxWidth(),
    ) {
            Box(modifier = modifier.size(strokeThickness)) {
                // Background track
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colorScheme.primary.copy(.3f), RoundedCornerShape(strokeThickness / 2))
                )

                // Active progress
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progressFraction.coerceIn(0f, 1f))
                        .fillMaxHeight()
                        .background(colorScheme.primary, RoundedCornerShape(strokeThickness / 2))
                )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(11) { index ->
                Column {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .height(8.dp)
                            .width(1.dp)
                            .background(colorScheme.onSurface)
                    )
                    Text(
                        text = "${valueRange.start.toInt() + index}",
                        fontSize = 12.sp,
                        color = contentColor,
                        modifier = Modifier
                            .wrapContentWidth(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

}

@PreviewLightDark
@Composable
private fun ProgressLinePreview() {
    RecoveryCompanionTheme {
        Surface(
            modifier = Modifier.size(
                width = 400.dp,
                height = 200.dp
            )
        ) {
                ProgressLine(
                    value = 4f,
                    modifier = Modifier.fillMaxWidth(),
                    contentColor = if (isSystemInDarkTheme()) Color.White else Color.Black
                )
        }
    }
}