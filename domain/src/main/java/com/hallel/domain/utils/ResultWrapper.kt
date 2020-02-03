package com.hallel.domain.utils

import java.lang.Exception

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T): ResultWrapper<T>()
    data class Error(val code: Int? = null, val error: Exception? = null): ResultWrapper<Nothing>()
}