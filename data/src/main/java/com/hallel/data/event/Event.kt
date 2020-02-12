package com.hallel.data.event

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Event.TABLE_NAME)
data class Event(
    @PrimaryKey @ColumnInfo(name = COLUMN_ID) val id: Int,
    @ColumnInfo(name = COLUMN_NAME) val name: String,
    @ColumnInfo(name = COLUMN_ACTIVE) val active: Int
) {

    companion object {
        const val TABLE_NAME = "event"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_ACTIVE = "active"
    }
}