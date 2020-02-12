package com.hallel.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hallel.data.event.Event
import com.hallel.data.event.EventDao
import com.hallel.data.eventContent.EventContent
import com.hallel.data.eventContent.EventContentDao
import com.hallel.data.user.User
import com.hallel.data.user.UserDao

@Suppress("SpellCheckingInspection")
@Database(
    entities = [User::class, Event::class, EventContent::class],//, Partner::class, Participant::class, Menu::class],
    version = 3
)

abstract class HallelDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun eventDao(): EventDao

    abstract fun eventContentDao(): EventContentDao

    /*abstract fun partnerDao(): PartnerDao

    abstract fun participantDao(): ParticipantDao





    abstract fun menuDao(): MenuDao*/
}