package com.checkout.frames.style.screen

import com.checkout.frames.style.screen.default.DefaultCountryPickerStyle

public data class PaymentFormStyle(
    public var paymentDetailsStyle: PaymentDetailsStyle = PaymentDetailsStyle(),
    public var countryPickerStyle: CountryPickerStyle = DefaultCountryPickerStyle.light()
)
