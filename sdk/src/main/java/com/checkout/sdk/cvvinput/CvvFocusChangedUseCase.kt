package com.checkout.sdk.cvvinput

import com.checkout.sdk.architecture.UseCase
import com.checkout.sdk.store.InMemoryStore

open class CvvFocusChangedUseCase(private val cvv: String,
                             private val hasFocus: Boolean,
                             private val store: InMemoryStore): UseCase<Boolean> {

    override fun execute(): Boolean {
        return !hasFocus && store.cvv.expectedLength != -1 && cvv.length < store.cvv.expectedLength
    }
}
