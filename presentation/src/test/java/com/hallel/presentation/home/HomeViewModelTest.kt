package com.hallel.presentation.home

import com.hallel.domain.event.EventContentUseCase
import com.hallel.domain.event.EventVO
import com.hallel.domain.guest.GuestType
import com.hallel.domain.guest.GuestUseCase
import com.hallel.domain.guest.GuestVO
import com.hallel.domain.sponsor.SponsorUseCase
import com.hallel.domain.sponsor.SponsorVO
import com.hallel.presentation.infra.BaseTest
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

class HomeViewModelTest: BaseTest() {

    @MockK
    private lateinit var eventContentUseCase: EventContentUseCase

    @MockK
    private lateinit var sponsorUseCase: SponsorUseCase

    @MockK
    private lateinit var guestUseCase: GuestUseCase

    private val viewModel by lazy {
        HomeViewModel(
            guestUseCase = guestUseCase,
            sponsorUseCase = sponsorUseCase,
            eventContentUseCase = eventContentUseCase,
            dispatchers = dispatchers
        )
    }

    @Test
    fun whenRequestEventContent_thenEventContentIsAvailable() {
        val eventVO = EventVO(
            id = 1,
            name = "name",
            title = "title",
            subtitle = "subtitle"
        )
        testDispatcher.runBlockingTest {
            every {
                eventContentUseCase.getEventContent(dispatchers.io)
            } returns flow { emit(eventVO) }

            viewModel.onLoadEventContent()

            assertEquals(true, viewModel.hasContentAvailable().value != null)
        }
    }

    @Test
    fun whenRequestEventContent_thenEventContentIsNotAvailable() {
        testDispatcher.runBlockingTest {
            every {
                eventContentUseCase.getEventContent(dispatchers.io)
            } returns flow { emit(null) }

            viewModel.onLoadEventContent()

            assertEquals(Unit, viewModel.noContentAvailable().value)
        }
    }

    @Test
    fun whenRequestSponsors_thenEventHasSponsors() {
        val sponsorVOList = listOf(SponsorVO (id = 1, name = "Name"))
        testDispatcher.runBlockingTest {
            every {
                sponsorUseCase.getSponsorsByEventId(any(), dispatchers.io)
            } returns flow { emit(sponsorVOList) }

            viewModel.onLoadEventSponsors(1)

            assertEquals(1, viewModel.hasSponsorAvailableForEvent().value?.size)
        }
    }

    @Test
    fun whenRequestSponsors_thenEventHasNoSponsorsAvailable() {
        testDispatcher.runBlockingTest {
            every {
                sponsorUseCase.getSponsorsByEventId(any(), dispatchers.io)
            } returns flow { emit(null) }

            viewModel.onLoadEventSponsors(1)

            assertEquals(Unit, viewModel.noSponsorAvailableForEvent().value)
        }
    }

    @Test
    fun whenRequestGuests_thenEventHasGuestsAvailable() {
        val guestVOList = listOf(GuestVO(
            id = 1,
            name = "Guest",
            type = listOf(GuestType.FATHER),
            image = null)
        )
        testDispatcher.runBlockingTest {
            every {
                guestUseCase.getGuestsFromEvent(any(), dispatchers.io)
            } returns flow { emit(guestVOList) }

            viewModel.onLoadEventGuests(1)

            assertEquals(1, viewModel.hasGuestAvailableForEvent().value?.size)
        }
    }

    @Test
    fun whenRequestGuests_thenEventHasNoGuestsAvailable() {
        testDispatcher.runBlockingTest {
            every {
                guestUseCase.getGuestsFromEvent(any(), dispatchers.io)
            } returns flow { emit(null) }

            viewModel.onLoadEventGuests(1)

            assertEquals(Unit, viewModel.noGuestAvailableForEvent().value)
        }
    }
}