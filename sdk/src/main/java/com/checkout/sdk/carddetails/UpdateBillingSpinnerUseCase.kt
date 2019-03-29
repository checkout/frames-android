package com.checkout.sdk.carddetails

import com.checkout.sdk.architecture.UseCase
import com.checkout.sdk.store.DataStore

open class UpdateBillingSpinnerUseCase(
    private val dataStore: DataStore,
    private val selectText: String,
    private val addText: String,
    private val editText: String,
    private val format: String
) : UseCase<List<String>?> {

    override fun execute(): List<String>? {
        return when {
            isDataStoreAddressValid() -> listOf(getFormattedAddress(), editText)
            dataStore.defaultBillingDetails != null -> listOf(getDefaultAddress(), editText)
            else -> clearBillingSpinner()
        }
    }

    private fun clearBillingSpinner(): List<String> {
        return listOf(selectText, addText)
    }

    private fun isDataStoreAddressValid(): Boolean {
        return !(dataStore.customerAddress1.isNullOrBlank() ||
                dataStore.customerAddress2.isNullOrBlank() ||
                dataStore.customerCity.isNullOrBlank() ||
                dataStore.customerState.isNullOrBlank())
    }

    private fun getFormattedAddress(): String {
        return format.format(
            dataStore.customerAddress1,
            dataStore.customerAddress2,
            dataStore.customerCity,
            dataStore.customerState
        )
    }

    // TODO: This is here to avoid breaking old behaviour; it will disappear soon once the
    // TODO: default address storage in DataStore is changed
    private fun getDefaultAddress(): String {
        return format.format(
            dataStore.defaultBillingDetails.addressLine1,
            dataStore.defaultBillingDetails.addressLine2,
            dataStore.defaultBillingDetails.city,
            dataStore.defaultBillingDetails.state
        )
    }
}
