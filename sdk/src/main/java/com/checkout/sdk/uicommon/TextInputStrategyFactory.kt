package com.checkout.sdk.uicommon

import com.checkout.sdk.billingdetails.strategy.*
import com.checkout.sdk.cvvinput.CvvStrategy
import com.checkout.sdk.store.InMemoryStore


class TextInputStrategyFactory {

    companion object {

        fun createStrategy(strategyKey: String): TextInputStrategy {
            val store = InMemoryStore.Factory.get()
            return when (strategyKey) {
                "cvv" -> CvvStrategy(store)
                "customer_name" -> CustomerNameStrategy(store)
                "address_one" -> AddressOneStrategy(store)
                "address_two" -> AddressTwoStrategy(store)
                "city" -> CityStrategy(store)
                "state" -> StateStrategy(store)
                "postcode" -> PostcodeStrategy(store)
                "phone_number" -> PhoneNumberStrategy(store)
                else -> {
                    throw IllegalArgumentException("Unknown class key: $strategyKey")
                }
            }
        }
    }
}
