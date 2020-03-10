package com.hallel.presentation.di

import com.hallel.presentation.access.AccessViewModel
import com.hallel.presentation.home.HomeViewModel
import com.hallel.presentation.main.MainViewModel
import com.hallel.presentation.splash.SplashViewModel
import com.hallel.presentation.utils.CustomDispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val PDISPATCHER = "p_dispatcher"
val presentationModule = module {

    single(named(PDISPATCHER)) { CustomDispatchers() }

    viewModel {
        MainViewModel(
            dispatchers = get(named(PDISPATCHER)),
            eventUseCase = get()
        )
    }

    viewModel {
        SplashViewModel(
            eventContentUseCase = get(),
            userUseCase = get(),
            updateUseCase = get(),
            dispatchers = get(named(PDISPATCHER))
        )
    }

    viewModel { AccessViewModel(get(), get(named(PDISPATCHER))) }

    viewModel {
        HomeViewModel(
            eventContentUseCase = get(),
            guestUseCase = get(),
            sponsorUseCase = get(),
            dispatchers = get(named(PDISPATCHER))
        )
    }
}