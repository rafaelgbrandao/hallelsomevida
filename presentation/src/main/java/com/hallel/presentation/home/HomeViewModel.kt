package com.hallel.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hallel.domain.event.EventContentUseCase
import com.hallel.domain.event.EventVO
import com.hallel.domain.guest.GuestUseCase
import com.hallel.domain.guest.GuestVO
import com.hallel.domain.sponsor.SponsorUseCase
import com.hallel.domain.sponsor.SponsorVO
import com.hallel.presentation.base.BaseViewModel
import com.hallel.presentation.utils.CustomDispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(
    private val eventContentUseCase: EventContentUseCase,
    private val guestUseCase: GuestUseCase,
    private val sponsorUseCase: SponsorUseCase,
    private val dispatchers: CustomDispatchers
): BaseViewModel() {

    fun hasContentAvailable(): LiveData<EventVO> = lvLoadEventContent
    private val lvLoadEventContent = MutableLiveData<EventVO>()

    fun noContentAvailable(): LiveData<Unit> = lvNoEventContentFound
    private val lvNoEventContentFound = MutableLiveData<Unit>()

    fun hasGuestAvailableForEvent(): LiveData<List<GuestVO>> = lvHasGuestAvailableForEvent
    private val lvHasGuestAvailableForEvent = MutableLiveData<List<GuestVO>>()

    fun noGuestAvailableForEvent(): LiveData<Unit> = lvNoGuestAvailableForEvent
    private val lvNoGuestAvailableForEvent = MutableLiveData<Unit>()

    fun hasSponsorAvailableForEvent(): LiveData<List<SponsorVO>> = lvHasSponsorAvailableForEvent
    private val lvHasSponsorAvailableForEvent = MutableLiveData<List<SponsorVO>>()

    fun noSponsorAvailableForEvent(): LiveData<Unit> = lvNoSponsorAvailableForEvent
    private val lvNoSponsorAvailableForEvent = MutableLiveData<Unit>()

    fun onLoadEventContent() {
        viewModelScope.launch(dispatchers.io) {
            eventContentUseCase.getEventContent(dispatchers.io).collect { event ->
                event?.let {
                    lvLoadEventContent.postValue(it)
                } ?: lvNoEventContentFound.postValue(Unit)
            }
        }
    }

    fun onLoadEventGuests(eventId: Int) {
        viewModelScope.launch(dispatchers.io) {
            guestUseCase.getGuestsFromEvent(dispatchers.io, eventId).collect { guestList ->
                when {
                    guestList.isEmpty() -> lvNoGuestAvailableForEvent.postValue(Unit)
                    else -> lvHasGuestAvailableForEvent.postValue(guestList)
                }
            }
        }
    }

    fun onLoadSponsors(eventId: Int) {
        viewModelScope.launch(dispatchers.io) {
            sponsorUseCase.getSponsorsByEventId(eventId, dispatchers.io).collect{ sponsorsList ->
                when {
                    sponsorsList.isEmpty() -> lvNoSponsorAvailableForEvent.postValue(Unit)
                    else -> lvHasSponsorAvailableForEvent.postValue(sponsorsList)
                }
            }
        }
    }
}