package com.checkout.sdk.core

import com.checkout.sdk.architecture.PresenterStore
import com.checkout.sdk.billingdetails.CustomerNameStrategy
import com.checkout.sdk.cvvinput.CvvStrategy
import com.checkout.sdk.cvvinput.TextInputPresenter
import com.checkout.sdk.store.InMemoryStore


class TextInputPresenterProvider {

    companion object {
        fun getOrCreatePresenter(key: String): TextInputPresenter {
            val strategy = when (key) {
                "text" -> CvvStrategy(InMemoryStore.Factory.get())
                "customer_name" -> CustomerNameStrategy(InMemoryStore.Factory.get())
                else -> {
                    throw IllegalArgumentException("Unknown class key: $key")
                }
            }
            return PresenterStore.getOrCreate(TextInputPresenter::class.java, { TextInputPresenter(strategy) } )
        }
    }
}
