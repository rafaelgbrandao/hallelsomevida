package com.hallel.domain.event

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

interface EventContentUseCase {

    fun getActiveEvent(dispatcher: CoroutineDispatcher): Flow<Int>

    fun getEventContent(dispatcher: CoroutineDispatcher): Flow<EventVO?>
}