package com.hallel.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hallel.data.user.User
import com.hallel.data.user.UserDao

@Suppress("SpellCheckingInspection")
@Database(
    entities = [User::class],//, Partner::class, Participant::class, Event::class, EventContent::class, Menu::class],
    version = 2
)

abstract class HallelDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    /*abstract fun partnerDao(): PartnerDao

    abstract fun participantDao(): ParticipantDao

    abstract fun eventDao(): EventDao

    abstract fun eventContentDao(): EventContentDao

    abstract fun menuDao(): MenuDao*/
}