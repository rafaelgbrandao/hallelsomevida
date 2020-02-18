package com.hallel.data.guest

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = EventGuest.TABLE_NAME)
data class EventGuest(
   @PrimaryKey @ColumnInfo(name = COLUMN_ID) val id: Int,
   @ColumnInfo(name = COLUMN_EVENT_ID) val eventId: Int,
   @ColumnInfo(name = COLUMN_PARTICIPANT_ID) val participantId: Int
) {
    companion object {
        const val TABLE_NAME = "event_guest"
        const val COLUMN_ID = "id"
        const val COLUMN_EVENT_ID = "event_id"
        const val COLUMN_PARTICIPANT_ID = "guest_id"
    }
}