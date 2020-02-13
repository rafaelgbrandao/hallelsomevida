package com.hallel.domain.guest

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

interface GuestUseCase {

    fun getGuestsFromEvent(dispatcher: CoroutineDispatcher, eventId: Int): Flow<List<GuestVO>>
}