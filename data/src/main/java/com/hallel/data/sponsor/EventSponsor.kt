package com.hallel.data.sponsor

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = EventSponsor.TABLE_NAME)
data class EventSponsor(
    @PrimaryKey @ColumnInfo(name = COLUMN_ID) val id: Int,
    @ColumnInfo(name = COLUMN_EVENT_ID) val eventId: Int,
    @ColumnInfo(name = COLUMN_SPONSOR_ID) val sponsorId: Int
) {
    companion object {
        const val TABLE_NAME = "event_sponsor"
        const val COLUMN_ID = "id"
        const val COLUMN_EVENT_ID = "event_id"
        const val COLUMN_SPONSOR_ID = "sponsor_id"
    }
}