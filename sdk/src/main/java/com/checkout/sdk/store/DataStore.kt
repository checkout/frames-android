package com.checkout.sdk.store

import com.checkout.sdk.core.Card

/**
 * The DataStore
 *
 *
 * Used to contain state within the SDK for easy communication between custom components.
 * It is also used preserve and restore state when in case the device orientation changes.
 */
open class DataStore protected constructor() {

    open var acceptedCards: List<Card>? = null

    object Factory {
        private val dataStore = DataStore()

        fun get() = dataStore
    }
}
