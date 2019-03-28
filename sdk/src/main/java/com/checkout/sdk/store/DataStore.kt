package com.checkout.sdk.store

import android.widget.LinearLayout
import com.checkout.sdk.core.Card
import com.checkout.sdk.models.BillingModel
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

    var isValidCardNumber = false
    var isValidCardMonth = false
    var isValidCardYear = false
    var isValidCardCvv = false

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

    var doneButtonText: String? = null
    var clearButtonText: String? = null

    var doneButtonLayout: LinearLayout.LayoutParams? = null
    var clearButtonLayout: LinearLayout.LayoutParams? = null

    var lastBillingValidState: BillingModel? = null
    var defaultBillingDetails: BillingModel? = null
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

        this.isValidCardNumber = false
        this.isValidCardMonth = false
        this.isValidCardYear = false
        this.isValidCardCvv = false

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
