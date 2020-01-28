package com.hallel.data.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = User.TABLE_NAME)
data class User(
    @PrimaryKey @ColumnInfo(name = COLUMN_ID) val userId: Int,
    @ColumnInfo(name = COLUMN_NAME) val userName: String,
    @ColumnInfo(name = COLUMN_EMAIL) val userEmail: String,
    @ColumnInfo(name = COLUMN_PHONE) val userPhone: String,
    @ColumnInfo(name = COLUMN_BIRTHDAY) val userBirthday: String,
    @ColumnInfo(name = COLUMN_IS_SENT) val isSent: Int
) {

    companion object {
        const val TABLE_NAME= "user"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PHONE = "phone"
        const val COLUMN_BIRTHDAY = "birthday"
        const val COLUMN_IS_SENT = "isSent"
    }
}