package com.aryanto.githubfinal.utils

import android.app.Application
import com.aryanto.githubfinal.di.appModule
import com.aryanto.githubfinal.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApps: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@BaseApps)
            modules(appModule, viewModelModule)
        }
    }
}