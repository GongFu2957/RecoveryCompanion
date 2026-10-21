package com.gongfu.recoverycompanion.di

import androidx.room.Room
import com.gongfu.recoverycompanion.logs.data.data_source.LogDatabase
import com.gongfu.recoverycompanion.logs.data.data_source.LogEntryDao
import com.gongfu.recoverycompanion.logs.data.repository.LogRepositoryImpl
import com.gongfu.recoverycompanion.logs.domain.repository.LogRepository
import com.gongfu.recoverycompanion.logs.presentation.add_edit_logentry.AddEditLogViewModel
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

    //Presentation Layer
    viewModelOf(::LogListViewModel)
    viewModelOf(::AddEditLogViewModel)
}