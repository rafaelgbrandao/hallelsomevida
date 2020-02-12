package com.hallel.domain.event

import com.hallel.data.eventContent.EventContent
import com.hallel.data.eventContent.EventContentDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

@FlowPreview
class EventContentUseCaseImpl(private val eventContentDao: EventContentDao): EventContentUseCase {

    override fun getEventContent(dispatcher: CoroutineDispatcher): Flow<EventVO?> {
        return flow {
            emit(
                transformEventContentInEventVO(
                    eventContentDao.getEventContent()
                )
            )
        }.flowOn(dispatcher)
    }

    private fun transformEventContentInEventVO(content: EventContent?): EventVO? {
        return content?.let {
            EventVO(
                id = it.eventId,
                name = "Hallel Franca",
                title = it.title,
                subtitle = it.subtitle,
                eventImage = it.imageBg
            )
        }
    }
}