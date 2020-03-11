package com.hallel.domain.event

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

interface EventContentUseCase {

    fun getActiveEvent(dispatcher: CoroutineDispatcher): Flow<Int?>

    fun getEventMenu(dispatcher: CoroutineDispatcher): Flow<List<MenuVO>?>

    fun getEventContent(dispatcher: CoroutineDispatcher): Flow<EventVO?>
}