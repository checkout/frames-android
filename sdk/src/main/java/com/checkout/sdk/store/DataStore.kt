package com.checkout.sdk.store

import com.checkout.sdk.core.Card
import com.checkout.sdk.billingdetails.model.BillingDetails
import java.util.*

/**
 * The DataStore
 *
 *
 * Used to contain state within the SDK for easy communication between custom components.
 * It is also used preserve and restore state when in case the device orientation changes.
 */
open class DataStore protected constructor() {
    var cardNumber = ""
    var cardMonth: String? = null
    var cardYear = Calendar.getInstance().get(Calendar.YEAR).toString()
    var cardCvv: String? = null
    var cvvLength = 4

    open var acceptedCards: List<Card>? = null

    var successUrl: String? = null
    var failUrl: String? = null

    var customerName = ""
    var defaultCustomerName: String? = null
    var customerCountry = ""
    var defaultCountry: Locale? = null
    open var customerAddress1 = ""
    open var customerAddress2 = ""
    open var customerCity = ""
    open var customerState = ""
    var customerZipcode = ""
    var customerPhonePrefix = ""
    var customerPhone = ""

    var isBillingCompleted = false

    var cardHolderLabel: String? = null
    var addressLine1Label: String? = null
    var addressLine2Label: String? = null
    var townLabel: String? = null
    var stateLabel: String? = null
    var postCodeLabel: String? = null
    var phoneLabel: String? = null

    var lastBillingValidState: BillingDetails? = null
    var defaultBillingDetails: BillingDetails? = null
    var lastCustomerNameState: String? = null

    fun cleanBillingData() {
        customerCountry = ""
        customerAddress1 = ""
        customerAddress2 = ""
        customerCity = ""
        customerState = ""
        customerZipcode = ""
        customerPhone = ""
    }

    fun cleanState() {
        this.cardNumber = ""
        this.cardMonth = "01"
        this.cardYear = Calendar.getInstance().get(Calendar.YEAR).toString()
        this.cardCvv = ""
        this.cvvLength = 4

        this.customerName = ""
        this.customerCountry = ""
        this.customerAddress1 = ""
        this.customerAddress2 = ""
        this.customerCity = ""
        this.customerState = ""
        this.customerZipcode = ""
        this.customerPhonePrefix = ""
        this.customerPhone = ""

        this.isBillingCompleted = false
    }

    object Factory {
        private val dataStore = DataStore()

        fun get() = dataStore
    }
}
