package com.hallel.domain.guest

import com.hallel.data.guest.Guest
import com.hallel.data.guest.GuestDao
import com.hallel.domain.BaseTest
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Ignore
import org.junit.Test

class GuestUseCaseImplTest: BaseTest() {

    @MockK
    private lateinit var guestDao: GuestDao

    private val useCase by lazy {
        GuestUseCaseImpl(guestDao)
    }

    @Test
    fun whenTransformGuestIntoGuestVO_thenAssertNull() {
        val guideVoList = useCase.transformGuestIntoGuestVO(null)

        assertEquals(null, guideVoList)
    }

    @Test
    fun whenTransformGuestIntoGuestVO_thenAssertTransformationIsCorrect() {
        val guestList = listOf(Guest(MOCK_ID, MOCK_NAME, MOCK_TYPE, MOCK_IMAGE))
        val guideVoList = useCase.transformGuestIntoGuestVO(guestList)

        assertEquals(1, guideVoList?.size)
        assertEquals(guideVoList?.get(0)?.name, MOCK_NAME)
        assertEquals(guideVoList?.get(0)?.id, MOCK_ID)
        assertEquals(guideVoList?.get(0)?.image, MOCK_IMAGE)
        assertEquals(guideVoList?.get(0)?.type, listOf(GuestType.PREACHER))
    }

    @Test
    fun givenGuestTypeNumber_whenConvertIt_thenAssertConversionIsOk() {
        listOf(1, 2, 3, 4, 5, 6, 7).forEach {
            val guestTypeList = useCase.getGuestTypeList(it)
            when (it) {
                1 -> {
                    assertEquals(1, guestTypeList.size)
                    assertEquals(true, guestTypeList.contains(GuestType.PREACHER))
                }
                2 -> {
                    assertEquals(1, guestTypeList.size)
                    assertEquals(true, guestTypeList.contains(GuestType.SINGER))
                }
                3 -> {
                    assertEquals(2, guestTypeList.size)
                    assertEquals(true, guestTypeList.contains(GuestType.PREACHER))
                    assertEquals(true, guestTypeList.contains(GuestType.SINGER))
                }
                4 -> {
                    assertEquals(1, guestTypeList.size)
                    assertEquals(listOf(GuestType.FATHER), guestTypeList)
                }
                5 -> {
                    assertEquals(2, guestTypeList.size)
                    assertEquals(true, guestTypeList.contains(GuestType.FATHER))
                    assertEquals(true, guestTypeList.contains(GuestType.PREACHER))
                }
                6 -> {
                    assertEquals(2, guestTypeList.size)
                    assertEquals(true, guestTypeList.contains(GuestType.FATHER))
                    assertEquals(true, guestTypeList.contains(GuestType.SINGER))
                }
                else -> {
                    assertEquals(3, guestTypeList.size)
                    assertEquals(true, guestTypeList.contains(GuestType.FATHER))
                    assertEquals(true, guestTypeList.contains(GuestType.SINGER))
                    assertEquals(true, guestTypeList.contains(GuestType.PREACHER))
                }
            }
        }
    }

    @Ignore("teste funciona se remover o flowOn do método")
    @Test
    fun givenEventId_whenLoadGuestsFromEvent_thenAssertEventHasGuest() {
        testDispatcher.runBlockingTest {
            every {
                guestDao.getGuestsFromEvent(any())
            } returns listOf(Guest(MOCK_ID, MOCK_NAME, MOCK_TYPE, MOCK_IMAGE))

            val guestVOList = useCase.getGuestsFromEvent(1, testDispatcher).take(1).first()

            assertEquals(1, guestVOList?.size)
            assertEquals(MOCK_ID, guestVOList?.get(0)?.id)
            assertEquals(MOCK_NAME, guestVOList?.get(0)?.name)
            assertEquals(MOCK_IMAGE, guestVOList?.get(0)?.image)
            assertEquals(1, guestVOList?.get(0)?.type?.size)
            assertEquals(true, guestVOList?.get(0)?.type?.contains(GuestType.PREACHER))
        }
    }

    @Ignore("teste funciona se remover o flowOn do método")
    @Test
    fun givenEventId_whenLoadGuestsFromEvent_thenAssertEventHasNoGuest() {
        testDispatcher.runBlockingTest {
            every {
                guestDao.getGuestsFromEvent(any())
            } returns null

            val guestVOList = useCase.getGuestsFromEvent(1, testDispatcher).take(1).first()

            assertEquals(emptyList<GuestVO>(), guestVOList)
        }
    }

    companion object {
        private const val MOCK_ID = 1
        private const val MOCK_NAME = "Test"
        private const val MOCK_TYPE = 1
        private const val MOCK_IMAGE = "fake url"
    }
}