package com.gongfu.recoverycompanion.di

import androidx.room.Room
import com.gongfu.recoverycompanion.logs.data.data_source.LogDatabase
import com.gongfu.recoverycompanion.logs.data.data_source.LogEntryDao
import com.gongfu.recoverycompanion.logs.data.repository.LogRepositoryImpl
import com.gongfu.recoverycompanion.logs.domain.repository.LogRepository
import com.gongfu.recoverycompanion.logs.domain.use_case.AddLog
import com.gongfu.recoverycompanion.logs.domain.use_case.DeleteLog
import com.gongfu.recoverycompanion.logs.domain.use_case.GetLog
import com.gongfu.recoverycompanion.logs.domain.use_case.GetLogs
import com.gongfu.recoverycompanion.logs.domain.use_case.LogUseCases
import com.gongfu.recoverycompanion.logs.presentation.loglist.LogListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    //Data Layer
    single {
        Room.databaseBuilder(
            androidContext(),
            LogDatabase::class.java,
            LogDatabase.DATABASE_NAME
        ).build()
    }
    single<LogEntryDao> { get<LogDatabase>().logEntryDao }
    single<LogRepository> { LogRepositoryImpl(get()) }

    //Domain Layer
    single {
        LogUseCases(
            getLogs = GetLogs(get()),
            deleteLog = DeleteLog(get()),
            addLog = AddLog(get()),
            getLog = GetLog(get())
        )
    }

    //Presentation Layer
    viewModelOf(::LogListViewModel)
}