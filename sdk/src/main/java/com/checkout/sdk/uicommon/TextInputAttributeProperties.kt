package com.checkout.sdk.uicommon

import android.content.Context
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo.*
import com.checkout.sdk.R
import com.checkout.sdk.cvvinput.TextInputPresenter


class TextInputAttributeProperties(
    val presenter: TextInputPresenter,
    val errorText: String,
    val imeFlag: Int
) {
    companion object {
        fun extractFromAttributes(
            context: Context,
            attrs: AttributeSet?
        ): TextInputAttributeProperties {
            if (attrs == null) {
                throw IllegalArgumentException("You must specify a presenter key: `app:presenter_key`")
            }
            val attributesArray = context.obtainStyledAttributes(attrs, R.styleable.TextInputView)
            val strategyKey = attributesArray.getString(R.styleable.TextInputView_strategy_key)
            val errorText = attributesArray.getString(R.styleable.TextInputView_error_text)
            val imeOptions = attributesArray.getString(R.styleable.TextInputView_ime_options)
            attributesArray.recycle()
            val imeFlag = convertFlagsStringToFlags(imeOptions)
            val presenter = TextInputPresenterFactory.getOrCreatePresenter(strategyKey)
            return TextInputAttributeProperties(presenter, errorText, imeFlag)
        }

        private fun convertFlagsStringToFlags(imeOptions: String): Int {
            val imeList = imeOptions.split("|")
            var imeInt = 0
            for (flagString in imeList) {
                when (flagString) {
                    "flagNoExtractUi" -> imeInt = imeInt.or(IME_FLAG_NO_EXTRACT_UI)
                    "actionDone" -> imeInt = imeInt.or(IME_ACTION_DONE)
                    "actionNext" -> imeInt = imeInt.or(IME_ACTION_NEXT)
                }
            }
            return imeInt
        }
    }


}
