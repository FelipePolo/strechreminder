package com.fpstudio.stretchreminder.util.di

import android.content.Context
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val imageModule = module {
    single {
        provideImageLoader(androidContext())
    }
}

private fun provideImageLoader(context: Context): ImageLoader {
    return ImageLoader.Builder(context)
        // Memory cache configuration
        .memoryCache {
            MemoryCache.Builder(context)
                .maxSizePercent(0.25) // Use 25% of app's available memory
                .build()
        }
        // Disk cache configuration
        .diskCache {
            DiskCache.Builder()
                .directory(context.cacheDir.resolve("image_cache"))
                .maxSizeBytes(50 * 1024 * 1024) // 50 MB
                .build()
        }
        // Cache policies
        .memoryCachePolicy(CachePolicy.ENABLED)
        .diskCachePolicy(CachePolicy.ENABLED)
        .networkCachePolicy(CachePolicy.ENABLED)
        // Enable crossfade animation
        .crossfade(true)
        .build()
}
