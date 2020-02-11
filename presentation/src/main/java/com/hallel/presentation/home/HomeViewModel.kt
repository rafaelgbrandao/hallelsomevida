package com.hallel.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hallel.domain.event.EventContentUseCase
import com.hallel.domain.event.EventVO
import com.hallel.presentation.base.BaseViewModel
import com.hallel.presentation.utils.CustomDispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(
    private val eventContentUseCase: EventContentUseCase,
    private val dispatchers: CustomDispatchers
): BaseViewModel() {

    fun hasContentAvailable(): LiveData<EventVO> = lvLoadEventContent
    private val lvLoadEventContent = MutableLiveData<EventVO>()

    fun noContentAvailable(): LiveData<Unit> = lvNoEventContentFound
    private val lvNoEventContentFound = MutableLiveData<Unit>()

    fun onLoadEventContent() {
        viewModelScope.launch(dispatchers.io) {
            eventContentUseCase.getEventContent(dispatchers.io).collect { event ->
                event?.let {
                    lvLoadEventContent.postValue(it)
                } ?: lvNoEventContentFound.postValue(Unit)
            }
        }
    }
}