package com.fpstudio.stretchreminder.di

import com.fpstudio.stretchreminder.ui.screen.form.FormViewModel
import com.fpstudio.stretchreminder.ui.screen.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val appModule = module {
    viewModel { FormViewModel(get()) }
    viewModelOf(::HomeViewModel)
}