package com.hallel.data.guest

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.hallel.data.event.Event

@Entity(
    tableName = EventGuest.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = Event::class,
            parentColumns = arrayOf(Event.COLUMN_ID),
            childColumns = arrayOf(EventGuest.COLUMN_EVENT_ID),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Guest::class,
            parentColumns = arrayOf(Guest.COLUMN_ID),
            childColumns = arrayOf(EventGuest.COLUMN_GUEST_ID),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class EventGuest(
    @PrimaryKey @ColumnInfo(name = COLUMN_ID) val id: Int,
    @ColumnInfo(name = COLUMN_EVENT_ID) val eventId: Int,
    @ColumnInfo(name = COLUMN_GUEST_ID) val participantId: Int
) {
    companion object {
        const val TABLE_NAME = "event_guest"
        const val COLUMN_ID = "id"
        const val COLUMN_EVENT_ID = "event_id"
        const val COLUMN_GUEST_ID = "guest_id"
    }
}