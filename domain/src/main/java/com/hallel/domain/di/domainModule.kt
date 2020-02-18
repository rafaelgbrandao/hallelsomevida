package com.hallel.domain.di

import com.hallel.domain.event.EventContentUseCase
import com.hallel.domain.event.EventContentUseCaseImpl
import com.hallel.domain.guest.GuestUseCase
import com.hallel.domain.guest.GuestUseCaseImpl
import com.hallel.domain.sponsor.SponsorUseCase
import com.hallel.domain.sponsor.SponsorUseCaseImpl
import com.hallel.domain.update.UpdateUseCase
import com.hallel.domain.update.UpdateUseCaseImpl
import com.hallel.domain.user.UserUseCase
import com.hallel.domain.user.UserUseCaseImpl
import kotlinx.coroutines.FlowPreview
import org.koin.dsl.module

@FlowPreview
val domainModule = module {

    single<UpdateUseCase> { UpdateUseCaseImpl() }

    single<UserUseCase> { UserUseCaseImpl(get()) }

    single<EventContentUseCase> { EventContentUseCaseImpl(get()) }

    single<GuestUseCase> { GuestUseCaseImpl(get()) }

    single<SponsorUseCase> { SponsorUseCaseImpl(get()) }
}