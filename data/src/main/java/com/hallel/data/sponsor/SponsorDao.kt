package com.hallel.data.sponsor

import androidx.room.Dao
import androidx.room.Query

@Dao
interface SponsorDao {

    @Query("$GET_SPONSORS_BY_ID :eventId")
    fun getSponsorByEventId(eventId:Int): List<Sponsor>

    companion object {

        const val GET_SPONSORS_BY_ID = "SELECT s.* from ${Sponsor.TABLE_NAME} as s " +
                "INNER JOIN ${EventSponsor.TABLE_NAME} as es " +
                "On s.${Sponsor.COLUMN_ID} = es.${EventSponsor.COLUMN_SPONSOR_ID} " +
                "where es.${EventSponsor.COLUMN_EVENT_ID} = "
    }
}