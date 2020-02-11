package com.hallel.presentation.di

import com.hallel.presentation.access.AccessViewModel
import com.hallel.presentation.home.HomeViewModel
import com.hallel.presentation.splash.SplashViewModel
import com.hallel.presentation.utils.CustomDispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val PDISPATCHER = "p_dispatcher"
val presentationModule = module {

    single(named(PDISPATCHER)) { CustomDispatchers() }

    viewModel { SplashViewModel(get(), get(), get(named(PDISPATCHER))) }

    viewModel { AccessViewModel(get(), get(named(PDISPATCHER))) }

    viewModel { HomeViewModel(get(), get(named(PDISPATCHER))) }
}