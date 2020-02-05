package com.hallel.presentation.base

import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.hallel.presentation.R

open class BaseActivity: AppCompatActivity() {

    fun setActionBarVisible(isVisible: Boolean) {
        when {
            isVisible -> supportActionBar?.show()
            else -> supportActionBar?.hide()
        }
    }

    fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    fun showAlertDialog(
        title: String?,
        message: String?,
        positiveButtonText: String? = null,
        positiveButtonListener: () -> Unit = { },
        negativeButtonText: String? = null,
        negativeButtonListener: () -> Unit = { },
        isCancelable: Boolean = true
    ) {
        AlertDialog.Builder(this).apply {
            title?.let { setTitle(title) }
            message?.let{ setMessage(message)}
            positiveButtonText?.let { btnText ->
                setPositiveButton(btnText) { _, _ -> positiveButtonListener() }
            }
            negativeButtonText?.let { btnText ->
                setNegativeButton(btnText) { _, _ -> negativeButtonListener() }
            }
            setCancelable(isCancelable)
        }.show()
    }

    fun showToast(text: String, length: Int = Toast.LENGTH_LONG){
        Toast.makeText(this, text, length).show()
    }

    fun navigateToScreen(screen: Int) {
        findNavController(R.id.nav_host_fragment).navigate(screen)
    }
}