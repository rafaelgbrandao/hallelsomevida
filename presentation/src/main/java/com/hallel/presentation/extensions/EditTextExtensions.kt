package com.hallel.presentation.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText
import com.redmadrobot.inputmask.MaskedTextChangedListener

fun EditText.applySingleMask(
    format: String = "",
    listener: () -> Unit = {}
) {
    val maskListener = MaskedTextChangedListener(
        format = format,
        field = this,
        valueListener = object : MaskedTextChangedListener.ValueListener {
            override fun onTextChanged(
                maskFilled: Boolean,
                extractedValue: String,
                formattedValue: String
            ) {
                listener()
            }
        }
    )
    this.addTextChangedListener(maskListener)
}

fun EditText.setTextChangeListener(onBefore: () -> Unit = {}, onAfter: () -> Unit = {}, onChange: () -> Unit = {}) {
    this.addTextChangedListener(
        object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) { onAfter() }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { onBefore() }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { onChange() }
        }
    )
}

fun TextInputEditText.getInputText(): String {
    return this.text.toString()
}