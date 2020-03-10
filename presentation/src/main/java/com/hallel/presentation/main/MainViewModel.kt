package com.hallel.presentation.main

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.hallel.domain.event.EventContentUseCase
import com.hallel.presentation.base.BaseViewModel
import com.hallel.presentation.utils.CustomDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(
    private val dispatchers: CustomDispatchers,
    private val eventUseCase: EventContentUseCase): BaseViewModel() {

    fun getMenuItens() {

    }
}