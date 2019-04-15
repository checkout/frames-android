package com.checkout.sdk.uicommon

import com.checkout.sdk.billingdetails.strategy.*
import com.checkout.sdk.cvvinput.CvvStrategy
import com.checkout.sdk.store.InMemoryStore


class TextInputStrategyFactory {

    companion object {

        fun createStrategy(strategyKey: String): TextInputStrategy {
            return when (strategyKey) {
                "cvv" -> CvvStrategy(InMemoryStore.Factory.get())
                "customer_name" -> CustomerNameStrategy(
                    InMemoryStore.Factory.get()
                )
                "address_one" -> AddressOneStrategy(
                    InMemoryStore.Factory.get()
                )
                "address_two" -> AddressTwoStrategy(
                    InMemoryStore.Factory.get()
                )
                "city" -> CityStrategy(InMemoryStore.Factory.get())
                "state" -> StateStrategy(InMemoryStore.Factory.get())
                "postcode" -> PostcodeStrategy(
                    InMemoryStore.Factory.get()
                )
                "phone_number" -> PhoneNumberStrategy(
                    InMemoryStore.Factory.get()
                )
                else -> {
                    throw IllegalArgumentException("Unknown class key: $strategyKey")
                }
            }
        }
    }
}
