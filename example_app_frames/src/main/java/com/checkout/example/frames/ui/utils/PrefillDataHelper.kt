package com.checkout.example.frames.ui.utils

import com.checkout.base.model.Country
import com.checkout.frames.screen.paymentform.model.BillingFormAddress
import com.checkout.frames.screen.paymentform.model.PrefillData
import com.checkout.tokenization.model.Address
import com.checkout.tokenization.model.Phone

internal object PrefillDataHelper {
    val prefillData = PrefillData(
        cardHolderName = "Test Name",
        billingFormAddress = BillingFormAddress(
            address = Address(
                addressLine1 = "Checkout.com",
                addressLine2 = "90 Tottenham Court Road",
                city = "London",
                state = "London",
                zip = "W1T 4TJ",
                country = Country.from(iso3166Alpha2 = "GB"),
            ),
            phone = Phone("7405987323", Country.UNITED_KINGDOM),
        ),
    )
}
