package com.fpstudio.stretchreminder.util.di

import com.fpstudio.stretchreminder.data.datasource.UserLocalDataSource
import com.fpstudio.stretchreminder.data.repository.UserRepositoryImpl
import com.fpstudio.stretchreminder.domain.repository.UserRepository
import com.fpstudio.stretchreminder.domain.usecase.GetUserUseCase
import com.fpstudio.stretchreminder.domain.usecase.SaveUserUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val userModule = module {
    single { UserLocalDataSource(androidContext()) }
    single { com.fpstudio.stretchreminder.util.notification.NotificationScheduler(androidContext()) }
    single<UserRepository> { UserRepositoryImpl(get(), get()) }
    factory { SaveUserUseCase(get()) }
    factory { GetUserUseCase(get()) }
}
