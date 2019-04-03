package com.checkout.sdk.uicommon

import com.checkout.sdk.architecture.UseCase
import com.checkout.sdk.uicommon.TextInputStrategy

open class TextInputFocusChangedUseCase(
    private val text: String,
    private val hasFocus: Boolean,
    private val strategy: TextInputStrategy
) : UseCase<Boolean> {

    override fun execute(): Boolean {
        return strategy.focusChanged(text, hasFocus)
    }
}
