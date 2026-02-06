package com.gongfu.recoverycompanion

import android.app.Application
import com.gongfu.recoverycompanion.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RecoveryCompanionApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RecoveryCompanionApp)
            androidLogger()

            modules(appModule)
        }
    }
}