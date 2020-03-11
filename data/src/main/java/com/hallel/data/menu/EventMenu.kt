package com.hallel.data.menu

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.hallel.data.event.Event

@Entity(
    tableName = EventMenu.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = Event::class,
            parentColumns = arrayOf(Event.COLUMN_ID),
            childColumns = arrayOf(EventMenu.COLUMN_EVENT_ID),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Menu::class,
            parentColumns = arrayOf(Menu.COLUMN_ID),
            childColumns = arrayOf(EventMenu.COLUMN_MENU_ID),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class EventMenu(
    @PrimaryKey @ColumnInfo(name = COLUMN_ID) val id: Int,
    @ColumnInfo(name = COLUMN_EVENT_ID) val eventId: Int,
    @ColumnInfo(name = COLUMN_MENU_ID) val menuId: Int
) {
    companion object {
        const val TABLE_NAME = "event_menu"
        const val COLUMN_ID = "id"
        const val COLUMN_EVENT_ID = "event_id"
        const val COLUMN_MENU_ID = "menu_id"
    }
}