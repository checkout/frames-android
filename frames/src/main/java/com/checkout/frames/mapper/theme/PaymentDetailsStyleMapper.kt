package com.checkout.frames.mapper.theme

import com.checkout.base.mapper.Mapper
import com.checkout.frames.R
import com.checkout.frames.model.Padding
import com.checkout.frames.style.component.CardSchemeComponentStyle
import com.checkout.frames.style.component.CardNumberComponentStyle
import com.checkout.frames.style.component.CvvComponentStyle
import com.checkout.frames.style.component.ExpiryDateComponentStyle
import com.checkout.frames.style.component.PayButtonComponentStyle
import com.checkout.frames.style.component.ScreenHeaderStyle
import com.checkout.frames.style.component.addresssummary.AddressSummaryComponentStyle
import com.checkout.frames.style.component.base.TextLabelStyle
import com.checkout.frames.style.component.default.DefaultCvvComponentStyle
import com.checkout.frames.style.component.default.DefaultAddressSummaryComponentStyle
import com.checkout.frames.style.component.default.DefaultCardNumberComponentStyle
import com.checkout.frames.style.component.default.DefaultExpiryDateComponentStyle
import com.checkout.frames.style.component.default.DefaultPayButtonComponentStyle
import com.checkout.frames.style.component.default.DefaultLightStyle
import com.checkout.frames.style.screen.PaymentDetailsStyle
import com.checkout.frames.style.theme.PaymentFormComponentField
import com.checkout.frames.style.theme.PaymentFormTheme
import com.checkout.frames.utils.constants.PaymentDetailsScreenConstants
import com.checkout.frames.utils.extensions.provideInputFieldStyle
import com.checkout.frames.utils.extensions.provideErrorMessageStyle
import com.checkout.frames.utils.extensions.provideButtonStyle
import com.checkout.frames.utils.extensions.provideContainerStyle
import com.checkout.frames.utils.extensions.provideSubTitleStyle
import com.checkout.frames.utils.extensions.provideTitleStyle
import com.checkout.frames.utils.extensions.provideTitleTextStyle
import com.checkout.frames.utils.extensions.provideText
import com.checkout.frames.utils.extensions.provideTextId

internal class PaymentDetailsStyleMapper : Mapper<PaymentFormTheme, PaymentDetailsStyle> {

    override fun map(from: PaymentFormTheme) = PaymentDetailsStyle(
        paymentDetailsHeaderStyle = providePaymentDetailsHeaderStyle(from),
        cardSchemeStyle = provideCardSchemeStyle(from),
        cardNumberStyle = provideCardNumberStyle(from),
        expiryDateStyle = provideExpiryDateStyle(from),
        cvvStyle = provideCVVStyle(from),
        addressSummaryStyle = provideAddressSummaryStyle(from),
        payButtonStyle = providePayButtonStyle(from),
        fieldsContainerStyle = from.provideContainerStyle(
            padding = Padding(
                start = PaymentDetailsScreenConstants.padding,
                end = PaymentDetailsScreenConstants.padding
            )
        )
    )

    private fun provideCardSchemeStyle(from: PaymentFormTheme): CardSchemeComponentStyle {
        var cardSchemeComponentStyle = DefaultLightStyle.cardSchemeComponentStyle()
        val paymentFormComponent = from.paymentFormComponents.find {
            PaymentFormComponentField.CardScheme.name == it.paymentFormComponentField.name
        }

        paymentFormComponent?.let { component ->
            cardSchemeComponentStyle = cardSchemeComponentStyle.copy(
                titleStyle = cardSchemeComponentStyle.titleStyle.provideTitleStyle(component, from) ?: TextLabelStyle()
            )
        }

        return cardSchemeComponentStyle
    }

    private fun provideCardNumberStyle(from: PaymentFormTheme): CardNumberComponentStyle {
        var cardNumberComponentStyle = DefaultCardNumberComponentStyle.light()
        val paymentFormComponent = from.paymentFormComponents.find {
            PaymentFormComponentField.CardNumber.name == it.paymentFormComponentField.name
        }

        paymentFormComponent?.let { component ->
            cardNumberComponentStyle = cardNumberComponentStyle.copy(
                inputStyle = with(cardNumberComponentStyle.inputStyle) {
                    this.copy(
                        titleStyle = titleStyle.provideTitleStyle(component, from),
                        inputFieldStyle = this.provideInputFieldStyle(from),
                        errorMessageStyle = errorMessageStyle.provideErrorMessageStyle(from)
                    )
                }
            )
        }

        return cardNumberComponentStyle
    }

    private fun provideAddressSummaryStyle(from: PaymentFormTheme): AddressSummaryComponentStyle {
        var addressSummaryComponentStyle = DefaultAddressSummaryComponentStyle.light()
        val paymentFormComponent = from.paymentFormComponents.find {
            PaymentFormComponentField.BillingSummary.name == it.paymentFormComponentField.name
        }

        paymentFormComponent?.let { component ->
            with(addressSummaryComponentStyle) {
                addressSummaryComponentStyle = copy(
                    titleStyle = titleStyle.provideTitleStyle(component, from),
                    subTitleStyle = subTitleStyle.provideSubTitleStyle(component, from),
                    addAddressButtonStyle = addAddressButtonStyle.provideButtonStyle(from, component),
                    summarySectionStyle = summarySectionStyle.copy(
                        editAddressButtonStyle = summarySectionStyle.editAddressButtonStyle.provideButtonStyle(
                            from,
                            component
                        ),
                        addressTextStyle = summarySectionStyle.addressTextStyle.copy(
                            textStyle = summarySectionStyle.addressTextStyle.provideTitleTextStyle(from)
                        )
                    )
                )
            }
        }

        return addressSummaryComponentStyle
    }

    private fun provideCVVStyle(from: PaymentFormTheme): CvvComponentStyle {
        var cvvComponentStyle = DefaultCvvComponentStyle.light()
        val paymentFormComponent = from.paymentFormComponents.find {
            PaymentFormComponentField.CVV.name == it.paymentFormComponentField.name
        }
        paymentFormComponent?.let { component ->
            cvvComponentStyle = cvvComponentStyle.copy(
                inputStyle = with(cvvComponentStyle.inputStyle) {
                    this.copy(
                        titleStyle = titleStyle.provideTitleStyle(component, from),
                        subtitleStyle = subtitleStyle.provideSubTitleStyle(component, from),
                        inputFieldStyle = provideInputFieldStyle(from),
                        errorMessageStyle = errorMessageStyle.provideErrorMessageStyle(from)
                    )
                }
            )
        }
        return cvvComponentStyle
    }

    private fun provideExpiryDateStyle(from: PaymentFormTheme): ExpiryDateComponentStyle {
        var expiryDateComponentStyle = DefaultExpiryDateComponentStyle.light()
        val paymentFormComponent = from.paymentFormComponents.find {
            PaymentFormComponentField.ExpiryDate.name == it.paymentFormComponentField.name
        }

        paymentFormComponent?.let { component ->
            expiryDateComponentStyle = expiryDateComponentStyle.copy(
                inputStyle = with(expiryDateComponentStyle.inputStyle) {
                    this.copy(
                        titleStyle = titleStyle.provideTitleStyle(component, from),
                        subtitleStyle = subtitleStyle.provideSubTitleStyle(component, from),
                        inputFieldStyle = provideInputFieldStyle(from),
                        errorMessageStyle = errorMessageStyle.provideErrorMessageStyle(from)
                    )
                }
            )
        }

        return expiryDateComponentStyle
    }

    private fun providePayButtonStyle(from: PaymentFormTheme): PayButtonComponentStyle {
        var payButtonComponentStyle = DefaultPayButtonComponentStyle.light()
        val paymentFormComponent = from.paymentFormComponents.find {
            PaymentFormComponentField.PaymentDetailsButton.name == it.paymentFormComponentField.name
        }

        paymentFormComponent?.let { component ->
            payButtonComponentStyle = payButtonComponentStyle.copy(
                buttonStyle = payButtonComponentStyle.buttonStyle.provideButtonStyle(from, component)
            )
        }

        return payButtonComponentStyle
    }

    private fun providePaymentDetailsHeaderStyle(from: PaymentFormTheme): ScreenHeaderStyle {
        var screenHeaderStyle = DefaultLightStyle.screenHeader(
            textId = R.string.cko_payment_details_title,
            imageId = R.drawable.cko_ic_arrow_back
        )
        val paymentFormComponent = from.paymentFormComponents.find {
            PaymentFormComponentField.PaymentHeaderTitle.name == it.paymentFormComponentField.name
        }
        paymentFormComponent?.let { component ->
            screenHeaderStyle = screenHeaderStyle.copy(
                text = screenHeaderStyle.text.provideText(component.titleText),
                textId = screenHeaderStyle.textId?.provideTextId(component.titleTextId),
                backIconStyle = screenHeaderStyle.backIconStyle.copy(
                    image = component.backIconImageResourceID,
                    tinColor = from.paymentFormThemeColors.imageColors.tinColor,
                ),
                textStyle = screenHeaderStyle.textStyle.copy(color = from.paymentFormThemeColors.textColors.titleColor),
                containerStyle = screenHeaderStyle.containerStyle.copy(
                    color = from.paymentFormThemeColors.paymentFormColors.background
                )
            )
        }

        return screenHeaderStyle
    }
}
