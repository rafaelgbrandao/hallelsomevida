package com.hallel.data.eventContent

import androidx.room.*
import com.hallel.data.event.Event

@Entity(
    tableName = EventContent.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = Event::class,
            parentColumns = arrayOf(Event.COLUMN_ID),
            childColumns = arrayOf(EventContent.COLUMN_EVENT_ID),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class EventContent(
    @PrimaryKey @ColumnInfo(name = COLUMN_ID) val id: Int,
    @ColumnInfo(name = COLUMN_EVENT_ID) val eventId: Int,
    @ColumnInfo(name = COLUMN_TITLE) val title: String?,
    @ColumnInfo(name = COLUMN_SUBTITLE) val subtitle: String?,
    @ColumnInfo(name = COLUMN_EVENT_BG) val imageBg: String?,
    @Embedded(prefix = "e.") val name: String?
) {

    companion object {
        const val TABLE_NAME = "event_content"
        const val COLUMN_ID = "id"
        const val COLUMN_EVENT_ID = "event_id"
        const val COLUMN_TITLE = "event_title"
        const val COLUMN_SUBTITLE = "event_subtitle"
        const val COLUMN_EVENT_BG = "event_img_bg"
    }
}