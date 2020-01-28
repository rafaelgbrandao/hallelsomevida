package com.hallel.presentation.di

import com.hallel.presentation.access.AccessViewModel
import com.hallel.presentation.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel { SplashViewModel(get(), get()) }

    viewModel { AccessViewModel(get()) }
}