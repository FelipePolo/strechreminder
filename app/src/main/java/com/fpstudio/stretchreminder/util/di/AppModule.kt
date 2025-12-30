package com.fpstudio.stretchreminder.util.di

import com.fpstudio.stretchreminder.ui.screen.exercise.ExerciseScreenViewModel
import com.fpstudio.stretchreminder.ui.screen.form.FormViewModel
import com.fpstudio.stretchreminder.ui.screen.home.HomeViewModel
import com.fpstudio.stretchreminder.ui.screen.threeyes.ThreeYesViewModel
import com.fpstudio.stretchreminder.ui.screen.tutorial.TutorialScreenViewModel
import com.fpstudio.stretchreminder.ui.screen.routine.RoutineSelectionViewModel
import com.fpstudio.stretchreminder.ui.screen.intro.IntroViewModel
import com.fpstudio.stretchreminder.ui.screen.settings.SettingsScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
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
}