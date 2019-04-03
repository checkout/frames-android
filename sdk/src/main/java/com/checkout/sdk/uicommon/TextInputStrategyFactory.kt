package com.checkout.sdk.uicommon

import com.checkout.sdk.billingdetails.CustomerNameStrategy
import com.checkout.sdk.cvvinput.CvvStrategy
import com.checkout.sdk.store.InMemoryStore


class TextInputStrategyFactory {

    companion object {

        fun createStrategy(strategyKey: String): TextInputStrategy {
            return when (strategyKey) {
                "cvv" -> CvvStrategy(InMemoryStore.Factory.get())
                "customer_name" -> CustomerNameStrategy(InMemoryStore.Factory.get())
                else -> {
                    throw IllegalArgumentException("Unknown class key: $strategyKey")
                }
            }
        }
    }
}
