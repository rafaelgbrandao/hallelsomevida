package com.hallel.domain.event

import com.hallel.data.event.EventDao
import com.hallel.data.eventContent.EventContent
import com.hallel.data.eventContent.EventContentDao
import com.hallel.data.menu.Menu
import com.hallel.data.menu.MenuDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

@FlowPreview
class EventContentUseCaseImpl(
    private val eventDao: EventDao,
    private val eventContentDao: EventContentDao,
    private val menuDao: MenuDao
): EventContentUseCase {

    override fun getActiveEvent(dispatcher: CoroutineDispatcher): Flow<Int?> {
        return flow {
            val eventId = eventDao.getActiveEvent()?.id
            emit(eventId)
        }.flowOn(dispatcher)
    }

    override fun getEventMenu(dispatcher: CoroutineDispatcher): Flow<List<MenuVO>?> {
        return flow {
            val menuList = menuDao.getMenuFromEvent()
            emit(transformMenuInMenuVO(menuList))
        }.flowOn(dispatcher)
    }

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

    private fun transformMenuInMenuVO(menuList: List<Menu>): List<MenuVO>? {
        return menuList.map {
            MenuVO(
                id = it.id,
                name = it.name
            )
        }
    }
}