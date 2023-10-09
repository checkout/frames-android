package com.checkout.example.frames.ui.extension

import android.content.Context
import com.checkout.example.frames.ui.utils.PromptUtils
import com.checkout.example.frames.ui.utils.PromptUtils.neutralButton

fun Context.showAlertDialog(title: String, message: String) {
    PromptUtils.alertDialog(this) {
        setTitle(title)
        setMessage(message)
        neutralButton {
            it.dismiss()
        }
    }.show()
}
