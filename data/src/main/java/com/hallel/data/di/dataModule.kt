package com.hallel.data.di

import com.hallel.data.BuildConfig
import com.hallel.data.database.HallelDatabase
import com.hallel.data.factory.DatabaseFactory
import com.hallel.data.factory.createRoomDb
import org.koin.dsl.module

val dataModule = module {

    single { DatabaseFactory(get()) }

    single { createRoomDb<HallelDatabase>(get(), BuildConfig.DATABASE_NAME, get()) }

    single { get<HallelDatabase>().userDao() }

    single { get<HallelDatabase>().eventDao() }

    single { get<HallelDatabase>().eventContentDao() }

    single { get<HallelDatabase>().guestDao() }

    single { get<HallelDatabase>().sponsorDao() }

}