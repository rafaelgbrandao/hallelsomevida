package com.hallel.data.guest

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Guest.TABLE_NAME)
data class Guest(
    @PrimaryKey @ColumnInfo(name = COLUMN_ID) val id: Int,
    @ColumnInfo(name = COLUMN_NAME) val name: String,
    @ColumnInfo(name = COLUMN_TYPE) val type: Int,
    @ColumnInfo(name = COLUMN_IMAGE) val image: String?
) {
    companion object {
        const val TABLE_NAME = "participant"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_TYPE = "type"
        const val COLUMN_IMAGE = "image"
    }
}