package com.gongfu.recoverycompanion.di

import com.gongfu.recoverycompanion.logs.presentation.loglist.LogListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    viewModelOf(::LogListViewModel)
}