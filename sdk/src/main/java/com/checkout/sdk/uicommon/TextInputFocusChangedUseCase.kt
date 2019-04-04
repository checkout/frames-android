package com.checkout.sdk.uicommon

import com.checkout.sdk.architecture.UseCase

open class TextInputFocusChangedUseCase(
    private val hasFocus: Boolean,
    private val strategy: TextInputStrategy
) : UseCase<Boolean> {

    override fun execute(): Boolean {
        return strategy.focusChanged(hasFocus)
    }
}
