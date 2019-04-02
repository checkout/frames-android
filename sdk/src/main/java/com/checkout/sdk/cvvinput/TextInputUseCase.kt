package com.checkout.sdk.cvvinput

import com.checkout.sdk.architecture.UseCase
import com.checkout.sdk.uicommon.TextInputStrategy


open class TextInputUseCase (
    open val text: String,
    private val strategy: TextInputStrategy
) : UseCase<Unit> {

    private constructor(builder: Builder) : this(
        builder.text,
        builder.strategy
    )

    override fun execute() {
        strategy.execute(text)
    }

    open class Builder (open val text: String) {
        lateinit var strategy: TextInputStrategy
            private set

        open fun strategy(strategy: TextInputStrategy) = apply { this.strategy = strategy }

        open fun build() = TextInputUseCase(this)
    }
}
