package com.hallel.data.factory

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

inline fun <reified T : RoomDatabase> createRoomDb(context: Context, dbName: String, databaseFactory: DatabaseFactory): T {
    databaseFactory.createDatabase()
    return Room
        .databaseBuilder(context.applicationContext, T::class.java, dbName)
        .fallbackToDestructiveMigration()
        .build()
}