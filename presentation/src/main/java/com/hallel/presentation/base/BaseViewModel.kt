package com.hallel.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Exception

open class BaseViewModel: ViewModel() {

    var activeEvent = 0
        private set

    fun showRequestError(): LiveData<Exception> = lvRequestError
    private val lvRequestError = MutableLiveData<Exception>()

    fun handleErrors(exception: Exception?) {
        lvRequestError.postValue(exception)
        /*when (exception) {
            is RuntimeException -> RuntimeException
            is Exception ->
            else -> GenericError
        }*/
    }

    fun updateActiveEvent(eventId: Int) {
        activeEvent = eventId
    }
}