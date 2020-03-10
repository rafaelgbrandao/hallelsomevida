package com.hallel.presentation.base

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hallel.presentation.R
import com.hallel.presentation.extensions.safeNavigate

open class BaseFragment: Fragment() {

    private val baseActivity: BaseActivity?
        get() = activity as? BaseActivity

    fun setActionBarVisible(isVisible: Boolean) = baseActivity?.setActionBarVisible(isVisible)

    fun setActionBarTitle(title: String) = baseActivity?.setActionBarTitle(title)

    fun enableLeftMenu(isMenuEnable: Boolean) = baseActivity?.enableLeftMenu(isMenuEnable)

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
        findNavController().safeNavigate(screen)
    }

    fun redirectDeepLink(link: String?) {
        link?.let {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(intent)
        } ?: showToast(getString(R.string.open_link_error))

    }
}