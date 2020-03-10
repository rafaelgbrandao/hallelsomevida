package com.hallel.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hallel.domain.event.EventContentUseCase
import com.hallel.domain.event.MenuVO
import com.hallel.presentation.base.BaseViewModel
import com.hallel.presentation.utils.CustomDispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect

class MainViewModel(
    private val dispatchers: CustomDispatchers,
    private val eventUseCase: EventContentUseCase): BaseViewModel() {

    fun updateMenuItems(): LiveData<List<MenuVO>?> = lvOnMenuItemsSuccess
    private val lvOnMenuItemsSuccess = MutableLiveData<List<MenuVO>?>()

    fun onGetEventMenuItems() {
        viewModelScope.async(dispatchers.io) {
            eventUseCase.getEventMenu(dispatchers.io).collect { menuList ->
                lvOnMenuItemsSuccess.postValue(menuList)
            }
        }
    }
}