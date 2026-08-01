package com.gongfu.recoverycompanion.logs.data.mappers

import com.gongfu.recoverycompanion.logs.data.data_source.LogEntryEntity
import com.gongfu.recoverycompanion.logs.domain.model.LogEntry
import com.gongfu.recoverycompanion.logs.domain.model.OutcomeType

fun LogEntry.toLogEntryEntity(): LogEntryEntity {
   return LogEntryEntity(
       id = id,
       timestamp = timestamp,
       title = title,
       description = description,
       trigger = trigger,
       location = location,
       intensityLevel = intensityLevel,
       bodyResponse = bodyResponse,
       outcomeCode = outcome.ordinal
   )
}

fun LogEntryEntity.toLogEntry(): LogEntry {
    return LogEntry(
        id = id,
        timestamp = timestamp,
        title = title,
        description = description,
        trigger = trigger,
        location = location,
        intensityLevel = intensityLevel,
        bodyResponse = bodyResponse,
        outcome = OutcomeType.entries.getOrElse(outcomeCode) { OutcomeType.NEUTRAL }
    )
}
