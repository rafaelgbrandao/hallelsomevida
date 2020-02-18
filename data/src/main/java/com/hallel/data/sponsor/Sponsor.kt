package com.hallel.data.sponsor

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Sponsor.TABLE_NAME)
data class Sponsor(
    @PrimaryKey @ColumnInfo(name = COLUMN_ID) val id: Int,
    @ColumnInfo(name = COLUMN_NAME) val name: String,
    @ColumnInfo(name = COLUMN_LINK) val link: String?,
    @ColumnInfo(name = COLUMN_IMG) val logo: String?
) {

    companion object {
        const val TABLE_NAME = "sponsor"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_LINK = "link"
        const val COLUMN_IMG = "image"
    }
}