package com.fpstudio.stretchreminder

import android.app.Application
import com.fpstudio.stretchreminder.util.di.appModule
import com.fpstudio.stretchreminder.util.di.userModule
import com.fpstudio.stretchreminder.util.di.networkModule
import com.fpstudio.stretchreminder.util.di.videoModule
import com.fpstudio.stretchreminder.util.di.imageModule
import com.fpstudio.stretchreminder.util.di.videoCacheModule
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
        com.revenuecat.purchases.Purchases.logLevel = if (BuildConfig.DEBUG) com.revenuecat.purchases.LogLevel.DEBUG else com.revenuecat.purchases.LogLevel.INFO
        com.revenuecat.purchases.Purchases.configure(
            com.revenuecat.purchases.PurchasesConfiguration.Builder(this, BuildConfig.REVENUECAT_API_KEY).build()
        )
    }
}