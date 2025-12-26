package com.fpstudio.stretchreminder.util.di

import com.fpstudio.stretchreminder.data.remote.ApiClient
import com.fpstudio.stretchreminder.data.remote.VideoApiService
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {
    single<Retrofit> { ApiClient.retrofit }
    single<VideoApiService> { get<Retrofit>().create(VideoApiService::class.java) }
}
