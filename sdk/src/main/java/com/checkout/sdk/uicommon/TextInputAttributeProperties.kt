package com.checkout.sdk.uicommon

import android.content.Context
import android.util.AttributeSet
import com.checkout.sdk.R
import com.checkout.sdk.cvvinput.TextInputPresenter


class TextInputAttributeProperties(
    val presenter: TextInputPresenter,
    val errorText: String
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
            attributesArray.recycle()
            val presenter = TextInputPresenterFactory.getOrCreatePresenter(strategyKey)
            return TextInputAttributeProperties(presenter, errorText)
        }
    }
}
