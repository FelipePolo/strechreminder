package com.fpstudio.stretchreminder.util.di

import com.fpstudio.stretchreminder.data.datasource.RoutineSessionsLocalDataSource
import com.fpstudio.stretchreminder.data.repository.RoutineSessionRepositoryImpl
import com.fpstudio.stretchreminder.domain.repository.RoutineSessionRepository
import com.fpstudio.stretchreminder.domain.usecase.GetRoutineStatsUseCase
import com.fpstudio.stretchreminder.domain.usecase.SaveRoutineSessionUseCase
import com.fpstudio.stretchreminder.domain.usecase.GetSubscriptionInfoUseCase
import com.fpstudio.stretchreminder.ui.screen.exercise.ExerciseScreenViewModel
import com.fpstudio.stretchreminder.ui.screen.form.FormViewModel
import com.fpstudio.stretchreminder.ui.screen.home.HomeViewModel
import com.fpstudio.stretchreminder.ui.screen.threeyes.ThreeYesViewModel
import com.fpstudio.stretchreminder.ui.screen.tutorial.TutorialScreenViewModel
import com.fpstudio.stretchreminder.ui.screen.routine.RoutineSelectionViewModel
import com.fpstudio.stretchreminder.ui.screen.intro.IntroViewModel
import com.fpstudio.stretchreminder.ui.screen.settings.SettingsScreenViewModel
import com.fpstudio.stretchreminder.ui.screen.premium.PremiumScreenViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    // Routine Session Tracking
    single { RoutineSessionsLocalDataSource(androidContext()) }
    single<RoutineSessionRepository> { RoutineSessionRepositoryImpl(get()) }
    single { SaveRoutineSessionUseCase(get()) }
    single { GetRoutineStatsUseCase(get()) }
    
    // RevenueCat
    single<com.fpstudio.stretchreminder.domain.repository.RevenueCatRepository> { com.fpstudio.stretchreminder.data.repository.RevenueCatRepositoryImpl() }
    single { com.fpstudio.stretchreminder.domain.usecase.CheckEntitlementUseCase(get()) }
    single { GetSubscriptionInfoUseCase(get()) }
    
    // ViewModels
    viewModel { FormViewModel(saveUserUseCase = get(), getUserUseCase = get()) }
    viewModelOf(::TutorialScreenViewModel)
    viewModel { parametersHolder ->
        ExerciseScreenViewModel(
            initialState = parametersHolder.get(),
            saveRoutineSessionUseCase = get()
        )
    }
    viewModelOf(::ThreeYesViewModel)
    viewModel { HomeViewModel(getRoutineStatsUseCase = get(), getUserUseCase = get()) }
    viewModel { RoutineSelectionViewModel(get()) }
    viewModel { IntroViewModel(get()) }
    viewModel { SettingsScreenViewModel(getUserUseCase = get(), saveUserUseCase = get(), getSubscriptionInfoUseCase = get()) }
    
    // Premium Screen
    viewModel { 
        com.fpstudio.stretchreminder.ui.screen.premium.PremiumScreenViewModel(
            revenueCatRepository = get(),
            checkEntitlementUseCase = get()
        )
    }
}