package com.checkout.sdk.cardinput


import com.checkout.sdk.architecture.UseCase
import com.checkout.sdk.store.InMemoryStore
import com.checkout.sdk.utils.CardUtils

open class CardFocusUseCase(
    private val hasFocus: Boolean,
    private val store: InMemoryStore
): UseCase<Boolean> {

    override fun execute(): Boolean {
        return !hasFocus && !CardUtils.isValidCard(store.cardNumber.value)
    }
}
