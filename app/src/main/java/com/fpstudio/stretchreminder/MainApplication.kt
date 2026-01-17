package com.fpstudio.stretchreminder

import android.app.Application
import com.fpstudio.stretchreminder.util.di.appModule
import com.fpstudio.stretchreminder.util.di.userModule
import com.fpstudio.stretchreminder.util.di.networkModule
import com.fpstudio.stretchreminder.util.di.videoModule
import com.fpstudio.stretchreminder.util.di.imageModule
import com.fpstudio.stretchreminder.util.di.videoCacheModule
import com.revenuecat.purchases.LogLevel
import com.revenuecat.purchases.Purchases.Companion.configure
import com.revenuecat.purchases.Purchases.Companion.logLevel
import com.revenuecat.purchases.PurchasesConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@MainApplication)
            modules(appModule, userModule, networkModule, videoModule, imageModule, videoCacheModule)
        }
        
        // Initialize RevenueCat
        logLevel = if (BuildConfig.DEBUG) LogLevel.DEBUG else LogLevel.INFO
        configure(
            PurchasesConfiguration.Builder(this, BuildConfig.REVENUECAT_API_KEY).build()
        )
    }
}