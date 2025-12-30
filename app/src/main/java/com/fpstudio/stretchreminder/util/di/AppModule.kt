package com.fpstudio.stretchreminder.util.di

import com.fpstudio.stretchreminder.data.repository.BillingRepositoryImpl
import com.fpstudio.stretchreminder.domain.repository.BillingRepository
import com.fpstudio.stretchreminder.domain.usecase.GetSubscriptionStatusUseCase
import com.fpstudio.stretchreminder.domain.usecase.PurchaseSubscriptionUseCase
import com.fpstudio.stretchreminder.domain.usecase.RestorePurchasesUseCase
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
    // Billing
    single<BillingRepository> { BillingRepositoryImpl(androidContext()) }
    single { PurchaseSubscriptionUseCase(get()) }
    single { RestorePurchasesUseCase(get()) }
    single { GetSubscriptionStatusUseCase(get()) }
    
    // ViewModels
    viewModel { FormViewModel(saveUserUseCase = get(), getUserUseCase = get()) }
    viewModelOf(::TutorialScreenViewModel)
    viewModel { parametersHolder ->
        ExerciseScreenViewModel(initialState = parametersHolder.get())
    }
    viewModelOf(::ThreeYesViewModel)
    viewModelOf(::HomeViewModel)
    viewModel { RoutineSelectionViewModel(get()) }
    viewModel { IntroViewModel(get()) }
    viewModel { SettingsScreenViewModel(getUserUseCase = get(), saveUserUseCase = get()) }
    viewModel { 
        PremiumScreenViewModel(
            billingRepository = get(),
            purchaseSubscriptionUseCase = get(),
            restorePurchasesUseCase = get(),
            getSubscriptionStatusUseCase = get()
        )
    }
}