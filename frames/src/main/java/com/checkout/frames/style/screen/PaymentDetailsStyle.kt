package com.checkout.frames.style.screen

import com.checkout.frames.style.component.CardNumberComponentStyle
import com.checkout.frames.style.component.PayButtonComponentStyle
import com.checkout.frames.style.component.CountryComponentStyle
import com.checkout.frames.style.component.CardSchemeComponentStyle
import com.checkout.frames.style.component.CvvComponentStyle
import com.checkout.frames.style.component.ExpiryDateComponentStyle
import com.checkout.frames.style.component.addresssummary.AddressSummaryComponentStyle
import com.checkout.frames.style.component.default.DefaultCardNumberComponentStyle
import com.checkout.frames.style.component.default.DefaultCountryComponentStyle
import com.checkout.frames.style.component.default.DefaultCvvComponentStyle
import com.checkout.frames.style.component.default.DefaultExpiryDateComponentStyle
import com.checkout.frames.style.component.default.DefaultLightStyle
import com.checkout.frames.style.component.default.DefaultAddressSummaryComponentStyle
import com.checkout.frames.style.component.default.DefaultPayButtonComponentStyle

public data class PaymentDetailsStyle(
    public var cardSchemeStyle: CardSchemeComponentStyle = DefaultLightStyle.cardSchemeComponentStyle(),
    public var cardNumberStyle: CardNumberComponentStyle = DefaultCardNumberComponentStyle.light(),
    public var expiryDateComponentStyle: ExpiryDateComponentStyle = DefaultExpiryDateComponentStyle.light(),
    public var cvvComponentStyle: CvvComponentStyle = DefaultCvvComponentStyle.light(),
    public var countryComponentStyle: CountryComponentStyle = DefaultCountryComponentStyle.light(),
    public var addressSummaryComponentStyle: AddressSummaryComponentStyle = DefaultAddressSummaryComponentStyle.light(),
    public var payButtonComponentStyle: PayButtonComponentStyle = DefaultPayButtonComponentStyle.light()
)
