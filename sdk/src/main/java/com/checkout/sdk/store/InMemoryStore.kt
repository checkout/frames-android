package com.checkout.sdk.store

import com.checkout.sdk.billingdetails.model.BillingDetail
import com.checkout.sdk.billingdetails.model.BillingDetails
import com.checkout.sdk.cardinput.CardNumber
import com.checkout.sdk.cvvinput.Cvv
import com.checkout.sdk.date.CardDate
import com.checkout.sdk.models.PhoneModel


open class InMemoryStore(
    open var cardDate: CardDate,
    open var cvv: Cvv,
    open var cardNumber: CardNumber,
    open var customerName: BillingDetail,
    open var billingDetails: BillingDetails
) {
    private var phoneModelListener: PhoneModelListener? = null

    private constructor() : this(
        CardDate.UNKNOWN,
        Cvv.UNKNOWN,
        CardNumber.UNKNOWN,
        BillingDetail(),
        BillingDetails()
    )

    object Factory {
        private val inMemoryStore = InMemoryStore()

        fun get() = inMemoryStore
    }

    fun updatePhoneModel(phoneModel: PhoneModel) {
        val oldPhoneModel = billingDetails.phone
        billingDetails = billingDetails.copy(phone = phoneModel)
        if (phoneModel.countryCode != oldPhoneModel.countryCode) {
            phoneModelListener?.onRepositoryChanged(this)
        }
    }

    open fun listenForCountryCodeChange(phoneModelListener: PhoneModelListener) {
        this.phoneModelListener = phoneModelListener
    }

    interface PhoneModelListener {
        fun onRepositoryChanged(store: InMemoryStore)
    }
}
