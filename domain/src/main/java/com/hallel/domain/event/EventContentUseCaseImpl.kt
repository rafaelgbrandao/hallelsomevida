package com.hallel.domain.event

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class EventContentUseCaseImpl: EventContentUseCase {

    override fun getEventContent(dispatcher: CoroutineDispatcher): Flow<EventVO?> {
        return flow {
            emit(mockedEventVO())
        }.flowOn(dispatcher)
    }

    private fun mockedEventVO() = EventVO(
        id = 1,
        name = "Hallel Franca",
        title = "Teste titulo",
        subtitle = "Teste subtitulo",
        eventImage = "https://www.hallel.org.br/images/banner-pop-up-hallel-2018-site.jpg"
    )
}