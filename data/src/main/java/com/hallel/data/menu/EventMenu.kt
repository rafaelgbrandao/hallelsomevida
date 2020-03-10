package com.hallel.data.menu

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = EventMenu.TABLE_NAME)
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