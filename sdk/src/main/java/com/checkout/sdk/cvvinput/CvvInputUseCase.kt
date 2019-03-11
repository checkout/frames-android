package com.checkout.sdk.cvvinput

import com.checkout.sdk.architecture.UseCase
import com.checkout.sdk.store.InMemoryStore


open class CvvInputUseCase(
    private val store: InMemoryStore,
    open val cvv: String
) : UseCase<Unit> {

    override fun execute() {
        store.cvv = store.cvv.copy(value = cvv)
    }
}
