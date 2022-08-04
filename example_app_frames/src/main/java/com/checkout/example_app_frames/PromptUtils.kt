package com.checkout.example_app_frames

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface

object PromptUtils {
    fun alertDialog(context: Context, dialogBuilder: AlertDialog.Builder.() -> Unit): Dialog {
        val builder = AlertDialog.Builder(context).also {
            it.setCancelable(false)
            it.dialogBuilder()
        }
        return builder.create()
    }

    fun AlertDialog.Builder.neutralButton(
        text: String = "Ok",
        handleClick: (dialogInterface: DialogInterface) -> Unit = {},
    ) {
        this.setNeutralButton(text) { dialogInterface, _ -> handleClick(dialogInterface) }
    }
}
