package com.checkout.sdk.core

import com.checkout.sdk.billingdetails.BillingDetailsValidator
import com.checkout.sdk.billingdetails.NetworkBillingModel
import com.checkout.sdk.request.CardTokenizationRequest
import com.checkout.sdk.store.InMemoryStore
import com.checkout.sdk.utils.DateFormatter


class RequestGenerator(
    private val inMemoryStore: InMemoryStore,
    private val dateFormatter: DateFormatter,
    private val cardDetailsValidator: CardDetailsValidator,
    private val billingDetailsValidator: BillingDetailsValidator
) {

    fun generate(): CardTokenizationRequest? {
        return if (cardDetailsValidator.isValid()) {
            val billingDetails = createNetworkBillingDetails()
            CardTokenizationRequest(
                inMemoryStore.cardNumber.value,
                inMemoryStore.customerName.value,
                dateFormatter.formatMonth(inMemoryStore.cardDate.month.monthInteger),
                inMemoryStore.cardDate.year.value.toString(),
                inMemoryStore.cvv.value,
                billingDetails
            )
        } else
            null
    }

    private fun createNetworkBillingDetails(): NetworkBillingModel? {
        return if (billingDetailsValidator.isValid()) {
            NetworkBillingModel.from(inMemoryStore.billingDetails)
        } else {
            null
        }
    }
}
