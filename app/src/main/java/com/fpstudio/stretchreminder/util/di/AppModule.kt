package com.fpstudio.stretchreminder.util.di

import com.fpstudio.stretchreminder.ui.screen.exercise.ExerciseScreenViewModel
import com.fpstudio.stretchreminder.ui.screen.form.FormViewModel
import com.fpstudio.stretchreminder.ui.screen.home.HomeViewModel
import com.fpstudio.stretchreminder.ui.screen.threeyes.ThreeYesViewModel
import com.fpstudio.stretchreminder.ui.screen.tutorial.TutorialViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModel { FormViewModel(saveUserUseCase = get(), getUserUseCase = get()) }
    viewModelOf(::TutorialViewModel)
    viewModel { parametersHolder ->
        ExerciseScreenViewModel(initialState = parametersHolder.get())
    }
    viewModelOf(::ThreeYesViewModel)
    viewModelOf(::HomeViewModel)
}