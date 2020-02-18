package com.hallel.domain.sponsor

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

interface SponsorUseCase {

    fun getSponsorsByEventId(eventId: Int, dispatcher: CoroutineDispatcher): Flow<List<SponsorVO>?>
}