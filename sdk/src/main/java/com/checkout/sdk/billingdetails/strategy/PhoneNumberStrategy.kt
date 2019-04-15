package com.checkout.sdk.billingdetails.strategy

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
            .replace("\\D", "")
        val newPhoneModel = store.billingDetails.phone.copy(number = newPhoneText)
        store.billingDetails = store.billingDetails.copy(phone = newPhoneModel)
    }

    override fun focusChanged(hasFocus: Boolean): Boolean {
        val phoneNumber = store.billingDetails.phone.number
        return phoneNumber.length < 3
    }

    override fun reset() {
        store.billingDetails = store.billingDetails.copy(phone = PhoneModel()) // ?
    }

}
