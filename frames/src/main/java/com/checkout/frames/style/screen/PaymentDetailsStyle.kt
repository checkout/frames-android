package com.checkout.frames.style.screen

import com.checkout.frames.style.component.CardNumberComponentStyle
import com.checkout.frames.style.component.CountryComponentStyle
import com.checkout.frames.style.component.CvvComponentStyle
import com.checkout.frames.style.component.ExpiryDateComponentStyle
import com.checkout.frames.style.component.default.DefaultCardNumberComponentStyle
import com.checkout.frames.style.component.default.DefaultCountryComponentStyle
import com.checkout.frames.style.component.default.DefaultCvvComponentStyle
import com.checkout.frames.style.component.default.DefaultExpiryDateComponentStyle

public data class PaymentDetailsStyle(
    public var cardNumberStyle: CardNumberComponentStyle = DefaultCardNumberComponentStyle.light(),
    public var expiryDateComponentStyle: ExpiryDateComponentStyle = DefaultExpiryDateComponentStyle.light(),
    public var cvvComponentStyle: CvvComponentStyle = DefaultCvvComponentStyle.light(),
    public var countryComponentStyle: CountryComponentStyle = DefaultCountryComponentStyle.light()
)
