package com.hallel.data.eventContent

import androidx.room.*
import com.hallel.data.event.Event

@Dao
interface EventContentDao {

    @Query(GET_CONTENT_FOR_ACTIVE_EVENT)
    fun getEventContent(): EventContent?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateEventContent(event: EventContent): Long

    @Delete
    fun deleteEventContent(event: EventContent)

    companion object {
        private const val GET_CONTENT_FOR_ACTIVE_EVENT = "Select " +
                "e.${Event.COLUMN_NAME}, ec.* from " +
                "${Event.TABLE_NAME} as e INNER JOIN ${EventContent.TABLE_NAME} as ec " +
                "On e.${Event.COLUMN_ID} = ec.${EventContent.COLUMN_EVENT_ID} " +
                "where e.${Event.COLUMN_ACTIVE} = 1"
    }
}