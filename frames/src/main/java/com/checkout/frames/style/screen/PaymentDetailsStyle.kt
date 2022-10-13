package com.checkout.frames.style.screen

import com.checkout.frames.R
import com.checkout.frames.style.component.CardNumberComponentStyle
import com.checkout.frames.style.component.CountryComponentStyle
import com.checkout.frames.style.component.CardSchemeComponentStyle
import com.checkout.frames.style.component.CvvComponentStyle
import com.checkout.frames.style.component.ExpiryDateComponentStyle
import com.checkout.frames.style.component.default.DefaultCardNumberComponentStyle
import com.checkout.frames.style.component.default.DefaultCountryComponentStyle
import com.checkout.frames.style.component.default.DefaultCvvComponentStyle
import com.checkout.frames.style.component.default.DefaultExpiryDateComponentStyle
import com.checkout.frames.style.component.default.DefaultLightStyle

public data class PaymentDetailsStyle(
    public var cardSchemeStyle: CardSchemeComponentStyle = DefaultLightStyle.cardSchemeComponentStyle(
        titleTextId = R.string.cko_accepted_cards_title
    ),
    public var cardNumberStyle: CardNumberComponentStyle = DefaultCardNumberComponentStyle.light(),
    public var expiryDateComponentStyle: ExpiryDateComponentStyle = DefaultExpiryDateComponentStyle.light(),
    public var cvvComponentStyle: CvvComponentStyle = DefaultCvvComponentStyle.light(),
    public var countryComponentStyle: CountryComponentStyle = DefaultCountryComponentStyle.light()
)
