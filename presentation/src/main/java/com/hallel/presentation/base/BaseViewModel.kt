package com.hallel.presentation.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Exception

open class BaseViewModel: ViewModel() {

    fun handleErrors(exception: Exception?) {
        Log.v("Teste", "Error - ${exception?.message}")
        /*when (exception) {
            is RuntimeException -> RuntimeException
            is Exception
            else -> GenericError
        }*/
    }
}