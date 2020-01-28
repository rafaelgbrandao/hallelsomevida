package com.hallel.domain.di

import com.hallel.domain.update.UpdateUseCase
import com.hallel.domain.update.UpdateUseCaseImpl
import kotlinx.coroutines.FlowPreview
import org.koin.dsl.module

@FlowPreview
val domainModule = module {

    single<UpdateUseCase> { UpdateUseCaseImpl() }
}