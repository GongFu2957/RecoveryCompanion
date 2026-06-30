package com.gongfu.recoverycompanion.logs.presentation.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatEpochMillis(epochMillis: Long): String {
    //TODO add 24 hour functionality
    val instant = Instant.ofEpochMilli(epochMillis)
    val dayOfMonth = instant.atZone(ZoneId.systemDefault()).dayOfMonth

    val ordinal = when {
        dayOfMonth % 100 in 11..13 -> "th"
        dayOfMonth % 10 == 1 -> "st"
        dayOfMonth % 10 == 2 -> "nd"
        dayOfMonth % 10 == 3 -> "rd"
        else -> "th"
    }

    val formatter = DateTimeFormatter
        .ofPattern("MMM d'$ordinal' yyyy h:mm a", Locale.US)
        .withZone(ZoneId.systemDefault())

    return formatter.format(instant)
}