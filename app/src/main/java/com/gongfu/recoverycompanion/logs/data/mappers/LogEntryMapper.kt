package com.gongfu.recoverycompanion.logs.data.mappers

import com.gongfu.recoverycompanion.logs.data.data_source.LogEntryEntity
import com.gongfu.recoverycompanion.logs.domain.model.LogEntry

fun LogEntry.toLogEntryEntity(): LogEntryEntity {
   return LogEntryEntity(
       id = id,
       dateTime = dateTime,
       title = title,
       description = description,
       trigger = trigger,
       location = location,
       intensityLevel = intensityLevel,
       bodyResponse = bodyResponse
   )
}

fun LogEntryEntity.toLogEntry(): LogEntry {
    return LogEntry(
        id = id,
        dateTime = dateTime,
        title = title,
        description = description,
        trigger = trigger,
        location = location,
        intensityLevel = intensityLevel,
        bodyResponse = bodyResponse
    )
}
