package com.fpstudio.stretchreminder.util.di

import androidx.room.Room
import com.fpstudio.stretchreminder.data.datasource.UserLocalDataSource
import com.fpstudio.stretchreminder.data.local.AppDatabase
import com.fpstudio.stretchreminder.data.repository.UserRepositoryImpl
import com.fpstudio.stretchreminder.domain.repository.UserRepository
import com.fpstudio.stretchreminder.domain.usecase.GetUserUseCase
import com.fpstudio.stretchreminder.domain.usecase.SaveUserUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val userModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
    single { get<AppDatabase>().userDao() }
    single { UserLocalDataSource(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
    factory { SaveUserUseCase(get()) }
    factory { GetUserUseCase(get()) }
}
