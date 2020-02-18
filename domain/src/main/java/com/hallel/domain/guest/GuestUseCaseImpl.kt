package com.hallel.domain.guest

import com.hallel.data.guest.Guest
import com.hallel.data.guest.GuestDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

@FlowPreview
class GuestUseCaseImpl(private val guestDao: GuestDao) : GuestUseCase {

    override fun getGuestsFromEvent(eventId: Int, dispatcher: CoroutineDispatcher): Flow<List<GuestVO>?> {
        return flow {
            val guestList = guestDao.getGuestsFromEvent(eventId)
            when {
                guestList.isNullOrEmpty() -> emit(emptyList())
                else -> emit(transformGuestIntoGuestVO(guestList))
            }
        }.flowOn(dispatcher)
    }

    internal fun transformGuestIntoGuestVO(guestList: List<Guest>?): List<GuestVO>? {
        return guestList?.map {
            GuestVO(
                id = it.id,
                name = it.name,
                type = getGuestTypeList(it.type),
                image = it.image
            )
        }
    }

    internal fun getGuestTypeList(type: Int): List<GuestType> {
        return when (type) {
            1 -> listOf(GuestType.PREACHER)
            2 -> listOf(GuestType.SINGER)
            3 -> listOf(GuestType.PREACHER, GuestType.SINGER)
            4 -> listOf(GuestType.FATHER)
            5 -> listOf(GuestType.PREACHER, GuestType.FATHER)
            6 -> listOf(GuestType.FATHER, GuestType.SINGER)
            else -> listOf(GuestType.FATHER, GuestType.PREACHER, GuestType.SINGER)
        }
    }
}