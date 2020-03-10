package com.hallel.data.menu

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Menu.TABLE_NAME)
data class Menu(
    @PrimaryKey @ColumnInfo(name = COLUMN_ID) val id: Int,
    @ColumnInfo(name = COLUMN_NAME) val name: String
) {
    companion object {
        const val TABLE_NAME = "menu"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
    }
}