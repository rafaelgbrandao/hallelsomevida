package com.hallel.data.guest

import androidx.room.Dao
import androidx.room.Query

@Dao
interface GuestDao {

    @Query("$GET_GUESTS_FROM_EVENT :eventID")
    fun getParticipantsFromEvent(eventID: Int): List<Guest>

    companion object {
        const val GET_GUESTS_FROM_EVENT = "Select p.* from ${Guest.TABLE_NAME} as p" +
                " INNER JOIN ${EventGuest.TABLE_NAME} as ep " +
                " ON p.${Guest.COLUMN_ID} = ep.${EventGuest.COLUMN_PARTICIPANT_ID}" +
                " where ep.${EventGuest.COLUMN_EVENT_ID} ="
    }
}