package com.hallel.presentation.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.hallel.presentation.utils.Event

inline fun <T> LiveData<T>.observe(
    owner: LifecycleOwner,
    crossinline observer: (T) -> Unit
): LiveData<T> {
    observe(owner, Observer<T> { result -> result?.let { observer(it) } })
    return this
}

inline fun <T> LiveData<Event<T>>.singleObserve(
    owner: LifecycleOwner,
    crossinline onEventUnhandledContent: (T) -> Unit
) {
    observe(owner, Observer {
            result -> result?.getContentIfNotHandled()?.let { onEventUnhandledContent(it) }
    })
}