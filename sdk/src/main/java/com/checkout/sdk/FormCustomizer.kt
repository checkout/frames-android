package com.checkout.sdk

import com.checkout.sdk.billingdetails.model.BillingDetail
import com.checkout.sdk.billingdetails.model.BillingDetails
import com.checkout.sdk.core.Card
import com.checkout.sdk.store.DataStore
import com.checkout.sdk.store.InMemoryStore


class FormCustomizer {

    private val mDataStore = DataStore.Factory.get()
    private val inMemoryStore = InMemoryStore.Factory.get()

    /**
     * This method is used set the accepted card schemes
     *
     * @param cards array of accepted cards
     */
    fun setAcceptedCards(cards: List<Card>): FormCustomizer {
        mDataStore.acceptedCards = cards
        return this
    }

    /**
     * This method used to inject address details if they have already been collected
     *
     * @param billing BillingModel representing the value for the billing details
     */
    fun injectBilling(billingModel: BillingModel): FormCustomizer {
        inMemoryStore.billingDetails = BillingDetails.from(billingModel)
        return this
    }

    /**
     * This method used to inject the cardholder name if it has already been collected
     *
     * @param name String representing the value for the cardholder name
     */
    fun injectCardHolderName(name: String): FormCustomizer {
        inMemoryStore.customerName = BillingDetail(name)
        return this
    }
}
