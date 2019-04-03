package com.checkout.sdk.uicommon

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo.*
import com.checkout.sdk.R


class TextInputAttributeProperties(
    val presenter: TextInputPresenter,
    val errorText: String,
    val imeFlag: Int,
    val digits: String?,
    val inputType: Int,
    val maxLength: Int
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
            val digits = attributesArray.getString(R.styleable.TextInputView_digits)
            val inputType =
                convertToInputType(attributesArray.getString(R.styleable.TextInputView_input_type))
            val maxLength =
                attributesArray.getInteger(R.styleable.TextInputView_max_length, Int.MAX_VALUE)
            attributesArray.recycle()
            val imeFlag = convertToImeFlag(imeOptions)
            val presenter = TextInputPresenterFactory.getOrCreatePresenter(strategyKey)
            return TextInputAttributeProperties(
                presenter,
                errorText,
                imeFlag,
                digits,
                inputType,
                maxLength
            )
        }

        private fun convertToInputType(inputTypeString: String): Int {
            return when (inputTypeString) {
                "number" -> InputType.TYPE_CLASS_NUMBER
                "text" -> InputType.TYPE_CLASS_TEXT
                else -> throw IllegalArgumentException("Unexpected value for inputTypeString $inputTypeString")
            }
        }

        private fun convertToImeFlag(imeOptions: String): Int {
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
