package com.hallel.data.event

import androidx.room.*

@Dao
interface EventDao {

    @Query("Select * from ${Event.TABLE_NAME} where ${Event.COLUMN_ACTIVE} = 1")
    fun getActiveEvent(): Event?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateEvent(event: Event): Long

    @Delete
    fun deleteEvent(event: Event)
}