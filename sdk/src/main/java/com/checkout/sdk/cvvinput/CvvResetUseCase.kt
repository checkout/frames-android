package com.checkout.sdk.cvvinput

import com.checkout.sdk.architecture.UseCase
import com.checkout.sdk.store.InMemoryStore

class CvvResetUseCase(private val store: InMemoryStore): UseCase<Unit> {

    override fun execute() {
        store.cvv = Cvv.UNKNOWN
    }
}
