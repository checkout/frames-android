package com.checkout.sdk.core

import com.checkout.sdk.models.BillingModel
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
        if (dataStore.isBillingCompleted) {
            request = CardTokenisationRequest(
                inMemoryStore.cardNumber.value,
                dataStore.customerName,
                dateFormatter.formatMonth(inMemoryStore.cardDate.month.monthInteger),
                inMemoryStore.cardDate.year.toString(),
                inMemoryStore.cvv.value,
                BillingModel(
                    dataStore.customerAddress1,
                    dataStore.customerAddress2,
                    dataStore.customerZipcode,
                    dataStore.customerCountry,
                    dataStore.customerCity,
                    dataStore.customerState,
                    PhoneModel(
                        dataStore.customerPhonePrefix,
                        dataStore.customerPhone
                    )
                )
            )
        } else {
            request = CardTokenisationRequest(
                inMemoryStore.cardNumber.value,
                dataStore.customerName,
                dateFormatter.formatMonth(inMemoryStore.cardDate.month.monthInteger),
                inMemoryStore.cardDate.year.value.toString(),
                inMemoryStore.cvv.value, null
            )
        }

        return request
    }
}
