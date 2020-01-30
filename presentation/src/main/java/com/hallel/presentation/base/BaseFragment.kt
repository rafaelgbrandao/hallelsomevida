package com.hallel.presentation.base

import android.widget.Toast
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {

    private val baseActivity: BaseActivity?
        get() = activity as? BaseActivity

    fun setActionBarVisible(isVisible: Boolean) = baseActivity?.setActionBarVisible(isVisible)

    fun showAlertDialog(
        title: String?,
        message: String?,
        positiveButtonText: String? = null,
        positiveButtonListener: () -> Unit = { },
        negativeButtonText: String? = null,
        negativeButtonListener: () -> Unit= { },
        isCancelable: Boolean = true
    ) {
        baseActivity?.showAlertDialog(
            title = title,
            message = message,
            positiveButtonText = positiveButtonText,
            positiveButtonListener = positiveButtonListener,
            negativeButtonText = negativeButtonText,
            negativeButtonListener = negativeButtonListener,
            isCancelable = isCancelable
        )
    }

    fun showToast(text: String, length: Int = Toast.LENGTH_LONG) {
        baseActivity?.showToast(text, length)
    }

    fun navigateToScreen(screen: Int) {
        baseActivity?.navigateToScreen(screen)
    }
}