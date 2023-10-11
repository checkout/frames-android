package com.checkout.frames.mapper.theme

import com.checkout.base.mapper.Mapper
import com.checkout.frames.R
import com.checkout.frames.model.BorderStroke
import com.checkout.frames.model.Margin
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
import com.checkout.frames.style.component.base.TextLabelStyle
import com.checkout.frames.style.component.default.DefaultAddressSummaryComponentStyle
import com.checkout.frames.style.component.default.DefaultCardHolderNameComponentStyle
import com.checkout.frames.style.component.default.DefaultCardNumberComponentStyle
import com.checkout.frames.style.component.default.DefaultCvvComponentStyle
import com.checkout.frames.style.component.default.DefaultExpiryDateComponentStyle
import com.checkout.frames.style.component.default.DefaultLightStyle
import com.checkout.frames.style.component.default.DefaultPayButtonComponentStyle
import com.checkout.frames.style.screen.PaymentDetailsStyle
import com.checkout.frames.style.theme.PaymentFormComponent
import com.checkout.frames.style.theme.PaymentFormComponentField
import com.checkout.frames.style.theme.PaymentFormTheme
import com.checkout.frames.utils.constants.AddressSummaryConstants
import com.checkout.frames.utils.constants.BorderConstants
import com.checkout.frames.utils.constants.ContainerConstants
import com.checkout.frames.utils.constants.PaymentDetailsScreenConstants
import com.checkout.frames.utils.extensions.provideContainerStyle
import com.checkout.frames.utils.extensions.provideErrorMessageStyle
import com.checkout.frames.utils.extensions.provideInfoStyle
import com.checkout.frames.utils.extensions.provideInputFieldStyle
import com.checkout.frames.utils.extensions.provideOutLinedButtonStyle
import com.checkout.frames.utils.extensions.provideSolidButtonStyle
import com.checkout.frames.utils.extensions.provideSubTitleStyle
import com.checkout.frames.utils.extensions.provideText
import com.checkout.frames.utils.extensions.provideTextId
import com.checkout.frames.utils.extensions.provideTitleStyle
import com.checkout.frames.utils.extensions.provideTitleTextStyle

@Suppress("TooManyFunctions")
internal class PaymentDetailsStyleMapper : Mapper<PaymentFormTheme, PaymentDetailsStyle> {

    override fun map(from: PaymentFormTheme) = PaymentDetailsStyle(
        paymentDetailsHeaderStyle = providePaymentDetailsHeaderStyle(from),
        cardSchemeStyle = provideCardSchemeStyle(from),
        cardHolderNameStyle = provideCardHolderNameStyle(from),
        cardNumberStyle = provideCardNumberStyle(from),
        expiryDateStyle = provideExpiryDateStyle(from),
        cvvStyle = provideCVVStyle(from),
        addressSummaryStyle = provideAddressSummaryStyle(from),
        payButtonStyle = providePayButtonStyle(from),
        fieldsContainerStyle = from.provideContainerStyle(
            padding = Padding(
                start = PaymentDetailsScreenConstants.padding,
                end = PaymentDetailsScreenConstants.padding,
            ),
        ),
    )

    private fun provideCardSchemeStyle(from: PaymentFormTheme): CardSchemeComponentStyle {
        var cardSchemeComponentStyle = DefaultLightStyle.cardSchemeComponentStyle()
        val paymentFormComponent = from.paymentFormComponents.find {
            PaymentFormComponentField.CardScheme.name == it.paymentFormComponentField.name
        }

        paymentFormComponent?.let { component ->
            cardSchemeComponentStyle = cardSchemeComponentStyle.copy(
                titleStyle = cardSchemeComponentStyle.titleStyle.provideTitleStyle(component, from) ?: TextLabelStyle(),
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
                        subtitleStyle = subtitleStyle.provideSubTitleStyle(component, from),
                        infoStyle = infoStyle.provideInfoStyle(component, from),
                        inputFieldStyle = this.provideInputFieldStyle(from),
                        errorMessageStyle = errorMessageStyle.provideErrorMessageStyle(from),
                        isInputFieldOptional = component.isFieldOptional,
                    )
                },
            )
        }

        return cardNumberComponentStyle
    }

    @Suppress("ComplexMethod")
    private fun provideAddressSummaryStyle(from: PaymentFormTheme): AddressSummaryComponentStyle? {
        val billingSummaryHeader = from.paymentFormComponents.find {
            PaymentFormComponentField.BillingSummaryHeader.name == it.paymentFormComponentField.name
        }

        val addButtonComponent = from.paymentFormComponents.find {
            PaymentFormComponentField.AddBillingSummaryButton.name == it.paymentFormComponentField.name
        }

        val editButtonComponent = from.paymentFormComponents.find {
            PaymentFormComponentField.EditBillingSummaryButton.name == it.paymentFormComponentField.name
        }

        val summaryPreviewComponent = from.paymentFormComponents.find {
            PaymentFormComponentField.BillingSummaryPreview.name == it.paymentFormComponentField.name
        }

        val isOptional: Boolean =
            summaryPreviewComponent?.isFieldOptional == true || addButtonComponent?.isFieldOptional == true ||
                editButtonComponent?.isFieldOptional == true || billingSummaryHeader?.isFieldOptional == true

        var addressSummaryComponentStyle = DefaultAddressSummaryComponentStyle.light(isOptional)

        billingSummaryHeader?.let { textComponent ->
            @Suppress("ComplexCondition")
            if (
                textComponent.isFieldHidden || summaryPreviewComponent?.isFieldHidden == true ||
                addButtonComponent?.isFieldHidden == true || editButtonComponent?.isFieldHidden == true
            ) {
                return null
            }

            addressSummaryComponentStyle = provideSummaryComponentStyle(
                addressSummaryComponentStyle,
                from,
                textComponent,
                addButtonComponent,
                editButtonComponent,
            )
        }

        return addressSummaryComponentStyle
    }

    private fun provideSummaryComponentStyle(
        addressSummaryComponentStyle: AddressSummaryComponentStyle,
        from: PaymentFormTheme,
        textComponent: PaymentFormComponent,
        addButtonComponent: PaymentFormComponent?,
        editButtonComponent: PaymentFormComponent?,
    ): AddressSummaryComponentStyle {
        return with(addressSummaryComponentStyle) {
            copy(
                titleStyle = titleStyle.provideTitleStyle(textComponent, from),
                subTitleStyle = provideAddressSummarySubTitle(subTitleStyle, textComponent, from),
                addAddressButtonStyle = addAddressButtonStyle.provideOutLinedButtonStyle(from, addButtonComponent),
                summarySectionStyle = summarySectionStyle.copy(
                    editAddressButtonStyle = summarySectionStyle.editAddressButtonStyle.provideOutLinedButtonStyle(
                        from,
                        editButtonComponent,
                    ),
                    addressTextStyle = summarySectionStyle.addressTextStyle.copy(
                        textStyle = summarySectionStyle.addressTextStyle.provideTitleTextStyle(from),
                    ),
                    dividerStyle = summarySectionStyle.dividerStyle?.copy(
                        color = from.paymentFormThemeColors.dividerColor.color,
                    ),
                    containerStyle = ContainerStyle(
                        borderStroke = BorderStroke(
                            width = BorderConstants.unfocusedBorderThickness,
                            color = from.paymentFormThemeColors.buttonColors.containerColor,
                        ),
                        margin = Margin(
                            top = AddressSummaryConstants.marginBeforeSummarySection,
                            bottom = AddressSummaryConstants.marginAfterSummarySection,
                        ),
                        cornerRadius = from.paymentFormCornerRadius.addressSummaryCornerRadius,
                        color = from.paymentFormThemeColors.inputFieldColors.inputFieldBackgroundColor,
                    ),
                ),
                containerStyle = containerStyle.copy(
                    shape = from.paymentFormShape.addressSummaryShape,
                    padding = Padding(start = ContainerConstants.paddingStart, end = ContainerConstants.paddingEnd),
                ),
            )
        }
    }

    /**
     * if addressSummary is optional, hide the subtitle Text for it
     */
    private fun provideAddressSummarySubTitle(
        subTitleStyle: TextLabelStyle?,
        component: PaymentFormComponent,
        from: PaymentFormTheme,
    ): TextLabelStyle? =
        if (component.isFieldOptional) null else subTitleStyle.provideSubTitleStyle(component, from)

    private fun provideCVVStyle(from: PaymentFormTheme): CvvComponentStyle? {
        var cvvComponentStyle = DefaultCvvComponentStyle.light()
        val paymentFormComponent = from.paymentFormComponents.find {
            PaymentFormComponentField.CVV.name == it.paymentFormComponentField.name
        }
        paymentFormComponent?.let { component ->
            if (component.isFieldHidden) return null
            cvvComponentStyle = cvvComponentStyle.copy(
                inputStyle = with(cvvComponentStyle.inputStyle) {
                    this.copy(
                        titleStyle = titleStyle.provideTitleStyle(component, from),
                        subtitleStyle = subtitleStyle.provideSubTitleStyle(component, from),
                        infoStyle = infoStyle.provideInfoStyle(component, from),
                        inputFieldStyle = provideInputFieldStyle(from),
                        errorMessageStyle = errorMessageStyle.provideErrorMessageStyle(from),
                        isInputFieldOptional = component.isFieldOptional,
                    )
                },
            )
        }
        return cvvComponentStyle
    }

    private fun provideCardHolderNameStyle(from: PaymentFormTheme): CardHolderNameComponentStyle? {
        var cardHolderNameStyle = DefaultCardHolderNameComponentStyle.light()
        val paymentFormComponent = from.paymentFormComponents.find {
            PaymentFormComponentField.CardHolderName.name == it.paymentFormComponentField.name
        }
        paymentFormComponent?.let { component ->
            if (component.isFieldHidden) return null

            cardHolderNameStyle = cardHolderNameStyle.copy(
                inputStyle = with(cardHolderNameStyle.inputStyle) {
                    this.copy(
                        titleStyle = titleStyle.provideTitleStyle(component, from),
                        subtitleStyle = subtitleStyle.provideSubTitleStyle(component, from),
                        infoStyle = component.infoTextId?.let { infoStyle.provideInfoStyle(component, from) },
                        inputFieldStyle = provideInputFieldStyle(from),
                        errorMessageStyle = errorMessageStyle.provideErrorMessageStyle(from),
                        isInputFieldOptional = component.isFieldOptional,
                    )
                },
            )
        }
        return cardHolderNameStyle
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
                        infoStyle = infoStyle.provideInfoStyle(component, from),
                        inputFieldStyle = provideInputFieldStyle(from),
                        errorMessageStyle = errorMessageStyle.provideErrorMessageStyle(from),
                        isInputFieldOptional = component.isFieldOptional,
                    )
                },
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
                buttonStyle = payButtonComponentStyle.buttonStyle.provideSolidButtonStyle(from, component),
            )
        }

        return payButtonComponentStyle
    }

    private fun providePaymentDetailsHeaderStyle(from: PaymentFormTheme): ScreenHeaderStyle {
        var screenHeaderStyle = DefaultLightStyle.screenHeader(
            textId = R.string.cko_payment_details_title,
            imageId = R.drawable.cko_ic_arrow_back,
        )
        val paymentFormComponent = from.paymentFormComponents.find {
            PaymentFormComponentField.PaymentHeaderTitle.name == it.paymentFormComponentField.name
        }
        paymentFormComponent?.let { component ->
            screenHeaderStyle = screenHeaderStyle.copy(
                text = screenHeaderStyle.text.provideText(component.titleText),
                textId = screenHeaderStyle.textId.provideTextId(component.titleText, component.titleTextId),
                backIconStyle = screenHeaderStyle.backIconStyle.copy(
                    image = component.backIconImageResourceID,
                    tinColor = from.paymentFormThemeColors.imageColors.tinColor,
                ),
                textStyle = screenHeaderStyle.textStyle.copy(color = from.paymentFormThemeColors.textColors.titleColor),
                containerStyle = screenHeaderStyle.containerStyle.copy(
                    color = from.paymentFormThemeColors.paymentFormColors.background,
                ),
            )
        }

        return screenHeaderStyle
    }
}
