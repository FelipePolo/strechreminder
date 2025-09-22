package com.fpstudio.stretchreminder

import android.app.Application
import com.fpstudio.stretchreminder.di.appModule
import com.fpstudio.stretchreminder.di.userModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@MainApplication)
            modules(appModule, userModule)
        }
    }
}