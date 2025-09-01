package com.fpstudio.stretchreminder.di

import com.fpstudio.stretchreminder.ui.screen.form.FormViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val appModule = module {
    viewModelOf(::FormViewModel)
}