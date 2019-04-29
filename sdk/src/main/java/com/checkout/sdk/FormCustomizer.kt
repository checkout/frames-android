package com.checkout.sdk

import com.checkout.sdk.billingdetails.model.BillingDetail
import com.checkout.sdk.billingdetails.model.BillingDetails
import com.checkout.sdk.core.Card
import com.checkout.sdk.store.InMemoryStore


open class FormCustomizer {

    private val inMemoryStore = InMemoryStore.Factory.get()
    private var acceptedCards: List<Card>? = null

    /**
     * This method is used set the accepted card schemes
     *
     * @param cards array of accepted cards
     */
    open fun setAcceptedCards(cards: List<Card>): FormCustomizer {
        acceptedCards = cards
        return this
    }

    open fun getAcceptedCards(): List<Card>? {
        return acceptedCards
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

    open class Factory {

        companion object {

            private val factory: FormCustomizer.Factory = Factory()

            fun get() = factory
        }

        private var formCustomizer: FormCustomizer? = null

        open fun getFormCustomizer(): FormCustomizer {
            return formCustomizer ?: throw UninitializedPropertyAccessException()
        }

        fun setFormCustomizer(formCustomizer: FormCustomizer) {
            this.formCustomizer = formCustomizer
        }
    }
}
