package com.checkout.sdk.carddetails

import com.checkout.sdk.architecture.UseCase
import com.checkout.sdk.core.Card
import com.checkout.sdk.store.DataStore

open class InitializeAcceptedCardsUseCase(private val dataStore: DataStore) : UseCase<List<Card>> {

    override fun execute(): List<Card> {
        return if (dataStore.acceptedCards != null) {
            dataStore.acceptedCards
        } else {
            val allCardsIncludingDefault = mutableListOf(*Card.values())
            allCardsIncludingDefault.remove(Card.DEFAULT)
            allCardsIncludingDefault
        }
    }
}
