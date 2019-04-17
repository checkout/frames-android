package com.checkout.sdk.uicommon

import com.checkout.sdk.architecture.UseCase
import com.checkout.sdk.store.InMemoryStore


class RepositoryChangeUseCase(
    private val strategy: TextInputStrategy,
    private val listener: InMemoryStore.PhoneModelListener
) : UseCase<Unit> {

    private constructor(builder: Builder) : this(
        builder.strategy,
        builder.listener
    )

    override fun execute() {
        strategy.listenForRepositoryChange(listener)
    }

    open class Builder(val strategy: TextInputStrategy) {
        lateinit var listener: InMemoryStore.PhoneModelListener
            private set

        open fun listener(listener: InMemoryStore.PhoneModelListener) = apply { this.listener = listener }

        open fun build() = RepositoryChangeUseCase(this)
    }
}
