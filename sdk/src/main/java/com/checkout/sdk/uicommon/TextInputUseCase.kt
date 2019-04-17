package com.checkout.sdk.uicommon

import com.checkout.sdk.architecture.UseCase


open class TextInputUseCase (
    open val text: String,
    private val strategy: TextInputStrategy
) : UseCase<Unit> {

    override fun execute() {
        strategy.textChanged(text)
    }
}
