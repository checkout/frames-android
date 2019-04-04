package com.checkout.sdk.core

import com.checkout.sdk.billingdetails.model.BillingDetails
import com.checkout.sdk.models.PhoneModel
import com.checkout.sdk.request.CardTokenisationRequest
import com.checkout.sdk.store.DataStore
import com.checkout.sdk.store.InMemoryStore
import com.checkout.sdk.utils.DateFormatter


class RequestGenerator(
    private val inMemoryStore: InMemoryStore,
    private val dataStore: DataStore,
    private val dateFormatter: DateFormatter
) {

    fun generate(): CardTokenisationRequest {
        val request: CardTokenisationRequest
        // TODO: Check validity on inMemoryStore
        if (dataStore.isBillingCompleted) {
            request = CardTokenisationRequest(
                inMemoryStore.cardNumber.value,
                inMemoryStore.customerName.value,
                dateFormatter.formatMonth(inMemoryStore.cardDate.month.monthInteger),
                inMemoryStore.cardDate.year.toString(),
                inMemoryStore.cvv.value,
                BillingDetails(
                    inMemoryStore.billingDetails.addressOne,
                    inMemoryStore.billingDetails.addressTwo,
                    inMemoryStore.billingDetails.city,
                    inMemoryStore.billingDetails.state,
                    inMemoryStore.billingDetails.postcode,
                    dataStore.customerCountry,
                    PhoneModel(
                        dataStore.customerPhonePrefix,
                        dataStore.customerPhone
                    )
                )
            )
        } else {
            request = CardTokenisationRequest(
                inMemoryStore.cardNumber.value,
                inMemoryStore.customerName.value,
                dateFormatter.formatMonth(inMemoryStore.cardDate.month.monthInteger),
                inMemoryStore.cardDate.year.value.toString(),
                inMemoryStore.cvv.value,
                null
            )
        }

        return request
    }
}
