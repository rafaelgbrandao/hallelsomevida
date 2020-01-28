package com.hallel.data.di

import com.hallel.data.BuildConfig
import com.hallel.data.database.HallelDatabase
import com.hallel.data.factory.DatabaseFactory
import com.hallel.data.factory.createRoomDb
import com.hallel.data.user.UserDao
import org.koin.dsl.module

val dataModule = module {

    single { DatabaseFactory(get()) }

    single { createRoomDb<HallelDatabase>(get(), BuildConfig.DATABASE_NAME, get()) }

    single { get<HallelDatabase>().userDao() }

}