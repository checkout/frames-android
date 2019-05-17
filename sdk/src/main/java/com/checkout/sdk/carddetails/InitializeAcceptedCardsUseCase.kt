package com.checkout.sdk.carddetails

import com.checkout.sdk.FormCustomizer
import com.checkout.sdk.architecture.UseCase
import com.checkout.sdk.core.Card

open class InitializeAcceptedCardsUseCase(private val formCustomizer: FormCustomizer?) : UseCase<List<Card>> {

    override fun execute(): List<Card> {
        val acceptedCards = formCustomizer?.getAcceptedCards()
        return if (acceptedCards == null) {
            val allCardsIncludingDefault = mutableListOf(*Card.values())
            allCardsIncludingDefault.remove(Card.DEFAULT)
            allCardsIncludingDefault
        } else {
            acceptedCards
        }
    }
}
