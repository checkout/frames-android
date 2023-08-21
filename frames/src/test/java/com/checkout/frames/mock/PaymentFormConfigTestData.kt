package com.checkout.frames.mock

import com.checkout.base.model.CardScheme
import com.checkout.base.model.Country
import com.checkout.frames.api.PaymentFlowHandler
import com.checkout.frames.screen.paymentform.model.BillingFormAddress
import com.checkout.frames.screen.paymentform.model.PrefillData
import com.checkout.frames.style.screen.PaymentFormStyle
import com.checkout.tokenization.model.Address
import com.checkout.tokenization.model.Phone
import com.checkout.tokenization.model.TokenDetails

internal object PaymentFormConfigTestData {

    private val country = Country.from(iso3166Alpha2 = "GB")

    val address = Address(
        addressLine1 = "Checkout.com",
        addressLine2 = "90 Tottenham Court Road",
        city = "London",
        state = "London",
        zip = "W1T 4TJ",
        country = country
    )

    val phone = Phone("4155552671", country)
    private val billingFormAddress = BillingFormAddress(
        name = "Test Billing Address name", address = address, phone = phone
    )
    val prefillData = PrefillData(
        cardHolderName = "Test Name", billingFormAddress = billingFormAddress
    )
    val style = PaymentFormStyle()
    val supportedCardSchemes = listOf(CardScheme.VISA, CardScheme.MAESTRO)
    const val publicKey = "Test key"

    @Suppress("EmptyFunctionBlock")
    val paymentFlowHandler = object : PaymentFlowHandler {
        override fun onSubmit() {}
        override fun onSuccess(tokenDetails: TokenDetails) {}
        override fun onFailure(errorMessage: String) {}
        override fun onBackPressed() {}
    }
}
