package com.checkout.example.frames.paymentformstyling

import com.checkout.example.frames.paymentformstyling.PaymentFormConstants.backIconSize
import com.checkout.example.frames.paymentformstyling.PaymentFormConstants.backgroundColor
import com.checkout.example.frames.paymentformstyling.PaymentFormConstants.inputFieldBorderShape
import com.checkout.example.frames.paymentformstyling.PaymentFormConstants.inputFieldColor
import com.checkout.example.frames.paymentformstyling.PaymentFormConstants.inputFieldCornerRadius
import com.checkout.example.frames.paymentformstyling.PaymentFormConstants.margin
import com.checkout.example.frames.paymentformstyling.PaymentFormConstants.marginBottom
import com.checkout.example.frames.paymentformstyling.PaymentFormConstants.marginTop
import com.checkout.example.frames.paymentformstyling.PaymentFormConstants.placeHolderTextColor
import com.checkout.example.frames.paymentformstyling.PaymentFormConstants.textColor
import com.checkout.frames.R
import com.checkout.frames.model.CornerRadius
import com.checkout.frames.model.Margin
import com.checkout.frames.model.Padding
import com.checkout.frames.model.Shape
import com.checkout.frames.model.font.FontWeight
import com.checkout.frames.style.component.CardHolderNameComponentStyle
import com.checkout.frames.style.component.CardNumberComponentStyle
import com.checkout.frames.style.component.CvvComponentStyle
import com.checkout.frames.style.component.ExpiryDateComponentStyle
import com.checkout.frames.style.component.PayButtonComponentStyle
import com.checkout.frames.style.component.addresssummary.AddressSummaryComponentStyle
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.InputFieldIndicatorStyle
import com.checkout.frames.style.component.base.TextLabelStyle
import com.checkout.frames.style.component.default.DefaultButtonStyle
import com.checkout.frames.style.component.default.DefaultAddressSummaryComponentStyle
import com.checkout.frames.style.component.default.DefaultLightStyle
import com.checkout.frames.style.component.default.DefaultImageStyle
import com.checkout.frames.style.component.default.DefaultTextLabelStyle
import com.checkout.frames.style.screen.PaymentDetailsStyle
import com.checkout.frames.utils.constants.ErrorConstants
import com.checkout.frames.utils.constants.PaymentButtonConstants

@Suppress("TooManyFunctions")
object CustomPaymentDetailsStyle {

    fun providePaymentDetailsStyle() = PaymentDetailsStyle(
        paymentDetailsHeaderStyle = provideHeaderStyle(),
        cardSchemeStyle = provideCardSchemeStyle(),
        cardNumberStyle = provideCardNumberStyle(),
        cardHolderNameStyle = provideCardHolderNameStyle(),
        expiryDateStyle = provideExpiryDateStyle(),
        cvvStyle = provideCVVStyle(),
        addressSummaryStyle = provideAddressSummaryStyle(),
        payButtonStyle = providePayButtonStyle(),
        fieldsContainerStyle = ContainerStyle(
            color = backgroundColor,
            padding = Padding(
                start = PaymentFormConstants.padding,
                end = PaymentFormConstants.padding
            )
        )
    )

    private fun providePayButtonStyle(): PayButtonComponentStyle {
        val buttonStyle = DefaultButtonStyle.lightSolid(
            textId = R.string.cko_pay,
            contentColor = inputFieldColor,
            containerColor = textColor,
            disabledContentColor = PaymentButtonConstants.disabledContentColor,
            disabledContainerColor = PaymentButtonConstants.disabledContainerColor,
            contentPadding = PaymentButtonConstants.contentPadding,
            fontWeight = FontWeight.Bold,
            margin = Margin(top = marginBottom),
            shape = Shape.Circle,
            cornerRadius = inputFieldCornerRadius
        )
        return PayButtonComponentStyle(buttonStyle = buttonStyle)
    }

    private fun provideAddressSummaryStyle(): AddressSummaryComponentStyle {
        var style = DefaultAddressSummaryComponentStyle.light(isOptional = false)
        val containerStyle =
            ContainerStyle(color = inputFieldColor, shape = Shape.RoundCorner, cornerRadius = inputFieldCornerRadius)

        style = style.copy(
            titleStyle = null,
            subTitleStyle = null,
            containerStyle = containerStyle,
            addAddressButtonStyle = DefaultButtonStyle.lightSolid(
                textId = R.string.cko_add_billing_address,
                trailingIconStyle = DefaultImageStyle.buttonTrailingImageStyle(tinColor = textColor),
                fontWeight = FontWeight.SemiBold,
                contentColor = textColor
            ),
            summarySectionStyle = style.summarySectionStyle.copy(
                editAddressButtonStyle = DefaultButtonStyle.lightSolid(
                    textId = R.string.cko_edit_billing_address,
                    trailingIconStyle = DefaultImageStyle.buttonTrailingImageStyle(tinColor = textColor),
                    fontWeight = FontWeight.SemiBold,
                    contentColor = textColor
                ),
                containerStyle = containerStyle
            )
        )
        return style
    }

    private fun provideCardNumberStyle(): CardNumberComponentStyle {
        val inputStyle = DefaultLightStyle.inputComponentStyle(
            placeholderResourceTextId = R.string.cko_card_number_title,
            withLeadingIcon = false,
            margin = Margin(top = 2)
        )
        return CardNumberComponentStyle(
            inputStyle.copy(
                errorMessageStyle = provideErrorMessageStyle(),
                inputFieldStyle = inputStyle.inputFieldStyle.copy(
                    containerStyle = provideContainerStyle(CornerRadius()),
                    indicatorStyle = provideIndicatorStyle(),
                    placeholderStyle = inputStyle.inputFieldStyle.placeholderStyle.copy(
                        color = placeHolderTextColor
                    )
                )
            )
        )
    }

    private fun provideCardHolderNameStyle(): CardHolderNameComponentStyle {
        val inputStyle = DefaultLightStyle.inputComponentStyle(
            placeholderResourceTextId = R.string.cko_card_holder_name_title,
            margin = Margin(top = 2),
            infoTextId = R.string.cko_input_field_optional_info
        )
        return CardHolderNameComponentStyle(
            inputStyle = inputStyle.copy(
                errorMessageStyle = provideErrorMessageStyle(),
                isInputFieldOptional = true,
                inputFieldStyle = inputStyle.inputFieldStyle.copy(
                    containerStyle = provideContainerStyle(inputFieldCornerRadius.copy(bottomStart = 0, bottomEnd = 0)),
                    indicatorStyle = provideIndicatorStyle(),
                    placeholderStyle = inputStyle.inputFieldStyle.placeholderStyle.copy(
                        color = placeHolderTextColor
                    )
                )
            )
        )
    }

    private fun provideExpiryDateStyle(): ExpiryDateComponentStyle {
        val inputStyle = DefaultLightStyle.inputComponentStyle(
            placeholderResourceTextId = R.string.cko_expiry_date_component_title,
            margin = Margin(top = 2)
        )
        return ExpiryDateComponentStyle(
            inputStyle.copy(
                errorMessageStyle = provideErrorMessageStyle(),
                inputFieldStyle = inputStyle.inputFieldStyle.copy(
                    containerStyle = provideContainerStyle(CornerRadius()),
                    indicatorStyle = provideIndicatorStyle(),
                    placeholderStyle = inputStyle.inputFieldStyle.placeholderStyle.copy(
                        color = placeHolderTextColor
                    )
                )
            )
        )
    }

    private fun provideCVVStyle(): CvvComponentStyle {
        val inputStyle = DefaultLightStyle.inputComponentStyle(
            placeholderResourceTextId = R.string.cko_cvv_component_title,
            margin = Margin(top = 2, bottom = margin)
        )
        return CvvComponentStyle(
            inputStyle.copy(
                errorMessageStyle = provideErrorMessageStyle(),
                inputFieldStyle = inputStyle.inputFieldStyle.copy(
                    containerStyle = provideContainerStyle(inputFieldCornerRadius.copy(topStart = 0, topEnd = 0)),
                    indicatorStyle = provideIndicatorStyle(),
                    placeholderStyle = inputStyle.inputFieldStyle.placeholderStyle.copy(
                        color = placeHolderTextColor
                    )
                )
            )
        )
    }

    private fun provideErrorMessageStyle(): TextLabelStyle =
        DefaultTextLabelStyle.error(
            padding = Padding(
                top = ErrorConstants.errorMessageTopPadding,
                bottom = ErrorConstants.errorMessageTopPadding
            )
        )

    private fun provideIndicatorStyle(): InputFieldIndicatorStyle = InputFieldIndicatorStyle.Underline(
        focusedUnderlineThickness = 0,
        unfocusedUnderlineThickness = 0
    )

    private fun provideContainerStyle(cornerRadius: CornerRadius): ContainerStyle {
        return ContainerStyle(
            shape = inputFieldBorderShape,
            color = inputFieldColor,
            cornerRadius = cornerRadius
        )
    }

    private fun provideCardSchemeStyle() = DefaultLightStyle.cardSchemeComponentStyle()
        .copy(
            containerStyle = ContainerStyle(
                margin = Margin(
                    top = marginTop,
                    bottom = marginBottom
                )
            ),
            titleStyle = DefaultTextLabelStyle.subtitle(textId = R.string.accepted_card_schemes, color = textColor),
            imageStyle = null
        )

    private fun provideHeaderStyle() = DefaultLightStyle.screenHeader(
        textId = R.string.cko_payment_details_title,
        imageId = R.drawable.ic_back_arrow,
        leadingIconSize = backIconSize,
        textColor = textColor,
        fontWeight = FontWeight.Bold
    ).copy(
        containerStyle = ContainerStyle(color = backgroundColor)
    )
}
