package com.gongfu.recoverycompanion.di

import com.gongfu.recoverycompanion.logs.presentation.logentry.LogEntryViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single<SimpleLogger> { SimpleLogger() }

    viewModelOf(::LogEntryViewModel)
}