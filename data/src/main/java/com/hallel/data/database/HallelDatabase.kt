package com.hallel.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hallel.data.event.Event
import com.hallel.data.event.EventDao
import com.hallel.data.eventContent.EventContent
import com.hallel.data.eventContent.EventContentDao
import com.hallel.data.guest.Guest
import com.hallel.data.guest.GuestDao
import com.hallel.data.guest.EventGuest
import com.hallel.data.user.User
import com.hallel.data.user.UserDao

@Suppress("SpellCheckingInspection")
@Database(
    entities = [User::class, Event::class, EventContent::class, Guest::class,
        EventGuest::class],//, Partner::class, Menu::class],
    version = 3
)

abstract class HallelDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun eventDao(): EventDao

    abstract fun eventContentDao(): EventContentDao

    abstract fun guestDao(): GuestDao

    /*abstract fun partnerDao(): PartnerDao

    abstract fun participantDao(): ParticipantDao





    abstract fun menuDao(): MenuDao*/
}