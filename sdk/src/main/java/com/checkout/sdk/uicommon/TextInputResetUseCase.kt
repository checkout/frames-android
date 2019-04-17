package com.checkout.sdk.uicommon

import com.checkout.sdk.architecture.UseCase

open class TextInputResetUseCase(private val strategy: TextInputStrategy): UseCase<Unit> {

    override fun execute() {
        strategy.reset()
    }
}
