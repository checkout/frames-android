package com.checkout.frames.style.screen

import com.checkout.frames.style.screen.default.DefaultCountryPickerStyle

public data class BillingFormStyle @JvmOverloads constructor(
    public var billingAddressDetailsStyle: BillingAddressDetailsStyle = BillingAddressDetailsStyle(),
    public var countryPickerStyle: CountryPickerStyle = DefaultCountryPickerStyle.light(),
)
