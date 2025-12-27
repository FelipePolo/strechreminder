package com.fpstudio.stretchreminder.util.di

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.cache.Cache
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import java.io.File

val videoCacheModule = module {
    single {
        provideVideoCache(androidContext())
    }
}

@OptIn(UnstableApi::class)
private fun provideVideoCache(context: Context): Cache {
    val cacheSize: Long = 200 * 1024 * 1024 // 200 MB
    val cacheDir = File(context.cacheDir, "video_cache")
    val evictor = LeastRecentlyUsedCacheEvictor(cacheSize)
    val databaseProvider = StandaloneDatabaseProvider(context)
    
    return SimpleCache(cacheDir, evictor, databaseProvider)
}
