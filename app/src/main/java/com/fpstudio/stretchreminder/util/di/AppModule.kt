package com.fpstudio.stretchreminder.util.di

import com.fpstudio.stretchreminder.data.datasource.RoutineSessionsLocalDataSource
import com.fpstudio.stretchreminder.data.datasource.RoutinesLocalDataSource
import com.fpstudio.stretchreminder.data.remote.ApiClient
import com.fpstudio.stretchreminder.data.repository.BodyPartRepository
import com.fpstudio.stretchreminder.data.repository.RoutineRepositoryImpl
import com.fpstudio.stretchreminder.data.repository.RoutineSessionRepositoryImpl
import com.fpstudio.stretchreminder.domain.repository.RoutineRepository
import com.fpstudio.stretchreminder.domain.repository.RoutineSessionRepository
import com.fpstudio.stretchreminder.domain.usecase.*
import com.fpstudio.stretchreminder.ui.screen.exercise.ExerciseScreenViewModel
import com.fpstudio.stretchreminder.ui.screen.form.FormViewModel
import com.fpstudio.stretchreminder.ui.screen.home.HomeViewModel
import com.fpstudio.stretchreminder.ui.screen.threeyes.ThreeYesViewModel
import com.fpstudio.stretchreminder.ui.screen.tutorial.TutorialScreenViewModel
import com.fpstudio.stretchreminder.ui.screen.routine.RoutineSelectionViewModel
import com.fpstudio.stretchreminder.ui.screen.intro.IntroViewModel
import com.fpstudio.stretchreminder.ui.screen.settings.SettingsScreenViewModel
import com.fpstudio.stretchreminder.domain.usecase.CheckNetworkConnectivityUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

import com.fpstudio.stretchreminder.data.repository.TemporaryAccessRepositoryImpl
import com.fpstudio.stretchreminder.domain.repository.TemporaryAccessRepository
import com.fpstudio.stretchreminder.domain.repository.AdMobRepository
import com.fpstudio.stretchreminder.data.repository.AdMobRepositoryImpl
import com.fpstudio.stretchreminder.data.repository.FeedbackRepositoryImpl
import com.fpstudio.stretchreminder.data.repository.RevenueCatRepositoryImpl
import com.fpstudio.stretchreminder.domain.repository.FeedbackRepository
import com.fpstudio.stretchreminder.domain.repository.RevenueCatRepository
import com.fpstudio.stretchreminder.ui.screen.feedback.FeedbackScreenViewModel
import com.fpstudio.stretchreminder.ui.screen.premium.PremiumScreenViewModel
import com.fpstudio.stretchreminder.util.NetworkConnectivityHelper

val appModule = module {
    // Routine Session Tracking
    single { RoutineSessionsLocalDataSource(androidContext()) }
    single<RoutineSessionRepository> { RoutineSessionRepositoryImpl(get()) }
    single { SaveRoutineSessionUseCase(get()) }
    single { GetRoutineStatsUseCase(get()) }
    single { CalculateStreakUseCase(get()) }
    
    // Saved Routines
    single { RoutinesLocalDataSource(androidContext()) }
    single<RoutineRepository> { RoutineRepositoryImpl(get()) }
    single { SaveRoutineUseCase(get()) }
    single { GetSavedRoutinesUseCase(get()) }
    single { HasSavedRoutinesUseCase(get()) }
    single { DeleteRoutineUseCase(get()) }
    
    // Body Parts
    single { BodyPartRepository(get()) }
    single { GetBodyPartsUseCase(get()) }
    
    // RevenueCat
    single<RevenueCatRepository> { RevenueCatRepositoryImpl() }
    single { CheckEntitlementUseCase(get()) }
    single { GetSubscriptionInfoUseCase(get()) }
    
    // Network Connectivity
    single { NetworkConnectivityHelper(androidContext()) }
    single { CheckNetworkConnectivityUseCase(get()) }
    
    // Temporary Access
    single<TemporaryAccessRepository> { TemporaryAccessRepositoryImpl() }
    
    // AdMob
    single<AdMobRepository> { AdMobRepositoryImpl() }
    
    // Feedback
    single<FeedbackRepository> {
        FeedbackRepositoryImpl(
            ApiClient.videoApiService
        ) 
    }
    single { SubmitFeedbackUseCase(get()) }
    
    // ViewModels
    viewModel { FormViewModel(saveUserUseCase = get(), getUserUseCase = get()) }
    viewModelOf(::TutorialScreenViewModel)
    viewModel { parametersHolder ->
        ExerciseScreenViewModel(
            initialState = parametersHolder.get(),
            saveRoutineSessionUseCase = get(),
            checkNetworkConnectivityUseCase = get(),
            checkEntitlementUseCase = get(),
            temporaryAccessRepository = get()
        )
    }
    viewModelOf(::ThreeYesViewModel)
    viewModel { HomeViewModel(getRoutineStatsUseCase = get(), getUserUseCase = get(), calculateStreakUseCase = get(), checkNetworkConnectivityUseCase = get()) }
    viewModel { RoutineSelectionViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get()) }
    viewModel { IntroViewModel(get()) }
    viewModel { SettingsScreenViewModel(getUserUseCase = get(), saveUserUseCase = get(), getSubscriptionInfoUseCase = get()) }
    
    // Premium Screen
    viewModel {
        PremiumScreenViewModel(
            checkEntitlementUseCase = get()
        )
    }
    
    // Feedback Screen
    viewModel {
        FeedbackScreenViewModel(
            submitFeedbackUseCase = get(),
            getSubscriptionInfoUseCase = get(),
            context = androidContext()
        )
    }
}