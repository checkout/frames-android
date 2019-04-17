package com.checkout.sdk.billingdetails.strategy

import com.checkout.sdk.billingdetails.MINIMUM_BILLING_DETAIL_LENGTH
import com.checkout.sdk.models.PhoneModel
import com.checkout.sdk.store.InMemoryStore
import com.checkout.sdk.uicommon.TextInputStrategy

class PhoneNumberStrategy(private val store: InMemoryStore) : TextInputStrategy {

    override fun getInitialValue(): String {
        val phoneModel = store.billingDetails.phone
        return phoneModel.countryCode + " " + phoneModel.number
    }

    override fun listenForRepositoryChange(listener: InMemoryStore.PhoneModelListener) {
        store.listenForCountryCodeChange(listener)
    }

    override fun textChanged(text: String) {
        val oldPhoneModel = store.billingDetails.phone
        val newPhoneText = text.replace(oldPhoneModel.countryCode, "")
            .replace(" ", "")
        val newPhoneModel = store.billingDetails.phone.copy(number = newPhoneText)
        store.billingDetails = store.billingDetails.copy(phone = newPhoneModel)
    }

    override fun focusChanged(hasFocus: Boolean): Boolean {
        return if (hasFocus) {
            false
        } else {
            val phoneNumber = store.billingDetails.phone.number
            phoneNumber.length < MINIMUM_BILLING_DETAIL_LENGTH
        }
    }

    override fun reset() {
        store.billingDetails = store.billingDetails.copy(phone = PhoneModel())
    }

}
