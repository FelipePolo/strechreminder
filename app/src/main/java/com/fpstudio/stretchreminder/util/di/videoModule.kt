package com.fpstudio.stretchreminder.util.di

import com.fpstudio.stretchreminder.data.repository.VideoRepositoryImpl
import com.fpstudio.stretchreminder.domain.repository.VideoRepository
import com.fpstudio.stretchreminder.domain.usecase.GetVideosUseCase
import org.koin.dsl.module

val videoModule = module {
    single<VideoRepository> { VideoRepositoryImpl(get()) }
    factory { GetVideosUseCase(get()) }
}
