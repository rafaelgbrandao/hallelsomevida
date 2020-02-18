package com.hallel.domain.guest

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

interface GuestUseCase {

    fun getGuestsFromEvent(eventId: Int, dispatcher: CoroutineDispatcher): Flow<List<GuestVO>?>
}