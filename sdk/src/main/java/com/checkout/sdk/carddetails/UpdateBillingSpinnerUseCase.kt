package com.checkout.sdk.carddetails

import com.checkout.sdk.architecture.UseCase
import com.checkout.sdk.billingdetails.BillingDetailsValidator
import com.checkout.sdk.store.InMemoryStore

open class UpdateBillingSpinnerUseCase(
    private val inMemoryStore: InMemoryStore,
    private val billingDetailsValidator: BillingDetailsValidator,
    private val selectText: String,
    private val addText: String,
    private val editText: String,
    private val format: String
) : UseCase<List<String>?> {

    override fun execute(): List<String>? {
        return when {
            billingDetailsValidator.isValid() -> listOf(getFormattedAddress(), editText)
            else -> clearBillingSpinner()
        }
    }

    private fun clearBillingSpinner(): List<String> {
        return listOf(selectText, addText)
    }

    private fun getFormattedAddress(): String {
        val billingDetails = inMemoryStore.billingDetails
        return format.format(
            billingDetails.addressOne.value,
            billingDetails.addressTwo.value,
            billingDetails.city.value,
            billingDetails.state.value
        )
    }
}
