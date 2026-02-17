package com.gongfu.recoverycompanion.logs.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LogEntryDao {

    @Query("SELECT * FROM logs_entries")
    fun getLogs(): Flow<List<LogEntryEntity>>

    @Query("SELECT * FROM logs_entries WHERE id = :id")
    suspend fun getLogEntryById(id: Long): LogEntryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: LogEntryEntity)

    @Delete
    suspend fun deleteLog(log: LogEntryEntity)
}