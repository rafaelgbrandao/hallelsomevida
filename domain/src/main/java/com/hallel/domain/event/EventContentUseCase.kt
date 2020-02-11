package com.hallel.domain.event

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

interface EventContentUseCase {

    fun getEventContent(dispatcher: CoroutineDispatcher): Flow<EventVO?>
}