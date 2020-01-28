package com.hallel.presentation.extensions

import android.view.View

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun goneViews(vararg views: View) {
    views.forEach {
        it.visibility = View.GONE
    }
}

fun visibleViews(vararg views: View) {
    views.forEach {
        it.visibility = View.VISIBLE
    }
}
