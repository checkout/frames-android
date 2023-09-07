package com.checkout.frames.style.screen

import com.checkout.frames.R
import com.checkout.frames.model.Padding
import com.checkout.frames.style.component.CardHolderNameComponentStyle
import com.checkout.frames.style.component.CardNumberComponentStyle
import com.checkout.frames.style.component.CardSchemeComponentStyle
import com.checkout.frames.style.component.CvvComponentStyle
import com.checkout.frames.style.component.ExpiryDateComponentStyle
import com.checkout.frames.style.component.PayButtonComponentStyle
import com.checkout.frames.style.component.ScreenHeaderStyle
import com.checkout.frames.style.component.addresssummary.AddressSummaryComponentStyle
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.default.DefaultAddressSummaryComponentStyle
import com.checkout.frames.style.component.default.DefaultCardHolderNameComponentStyle
import com.checkout.frames.style.component.default.DefaultCardNumberComponentStyle
import com.checkout.frames.style.component.default.DefaultCvvComponentStyle
import com.checkout.frames.style.component.default.DefaultExpiryDateComponentStyle
import com.checkout.frames.style.component.default.DefaultLightStyle
import com.checkout.frames.style.component.default.DefaultPayButtonComponentStyle
import com.checkout.frames.utils.constants.PaymentDetailsScreenConstants

public data class PaymentDetailsStyle @JvmOverloads constructor(
    public var paymentDetailsHeaderStyle: ScreenHeaderStyle = DefaultLightStyle.screenHeader(
        textId = R.string.cko_payment_details_title,
        imageId = R.drawable.cko_ic_arrow_back
    ),
    public var cardSchemeStyle: CardSchemeComponentStyle = DefaultLightStyle.cardSchemeComponentStyle(),
    public var cardHolderNameStyle: CardHolderNameComponentStyle? = DefaultCardHolderNameComponentStyle.light(),
    public var cardNumberStyle: CardNumberComponentStyle = DefaultCardNumberComponentStyle.light(),
    public var expiryDateStyle: ExpiryDateComponentStyle = DefaultExpiryDateComponentStyle.light(),
    public var cvvStyle: CvvComponentStyle? = DefaultCvvComponentStyle.light(),
    public var addressSummaryStyle: AddressSummaryComponentStyle? =
        DefaultAddressSummaryComponentStyle.light(),
    public var payButtonStyle: PayButtonComponentStyle = DefaultPayButtonComponentStyle.light(),
    public var fieldsContainerStyle: ContainerStyle = ContainerStyle(
        padding = Padding(
            start = PaymentDetailsScreenConstants.padding,
            end = PaymentDetailsScreenConstants.padding
        )
    )
)
