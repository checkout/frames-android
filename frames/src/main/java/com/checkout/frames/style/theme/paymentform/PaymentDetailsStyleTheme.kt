package com.checkout.frames.style.theme.paymentform

import androidx.annotation.RestrictTo
import com.checkout.frames.R
import com.checkout.frames.mapper.ThemeMapper
import com.checkout.frames.model.Padding
import com.checkout.frames.style.component.ScreenHeaderStyle
import com.checkout.frames.style.component.CardNumberComponentStyle
import com.checkout.frames.style.component.CardSchemeComponentStyle
import com.checkout.frames.style.component.ExpiryDateComponentStyle
import com.checkout.frames.style.component.CvvComponentStyle
import com.checkout.frames.style.component.PayButtonComponentStyle
import com.checkout.frames.style.component.addresssummary.AddressSummaryComponentStyle
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.TextStyle
import com.checkout.frames.style.component.default.DefaultLightStyle
import com.checkout.frames.style.component.default.DefaultCardNumberComponentStyle
import com.checkout.frames.style.component.default.DefaultExpiryDateComponentStyle
import com.checkout.frames.style.component.default.DefaultAddressSummaryComponentStyle
import com.checkout.frames.style.component.default.DefaultPayButtonComponentStyle
import com.checkout.frames.style.component.default.DefaultCvvComponentStyle
import com.checkout.frames.style.screen.PaymentDetailsStyle
import com.checkout.frames.style.theme.PaymentFormTheme
import com.checkout.frames.style.theme.RequestThemeStyles
import com.checkout.frames.utils.constants.PaymentDetailsScreenConstants

@Suppress("TooManyFunctions", "LongParameterList")
public object PaymentDetailsStyleTheme {

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    public fun providePaymentStyle(theme: PaymentFormTheme): PaymentDetailsStyle {

        return buildPaymentDetailsStyle(
            headerStyle = providePaymentDetailsHeader(theme),
            cardSchemeComponentStyle = provideCardSchemeComponentStyle(theme),
            cardNumberComponentStyle = provideCardNumberComponentStyle(theme),
            expiryDateComponentStyle = provideExpiryDateComponentStyle(theme),
            payButtonComponentStyle = providePaymentButtonStyle(theme),
            cvvComponentStyle = provideCvvComponentStyle(theme),
            addressSummaryComponentStyle = provideAddressSummaryComponentStyle(theme),
            fieldsComponentContainerStyle = ContainerStyle(
                padding = Padding(
                    start = PaymentDetailsScreenConstants.padding,
                    end = PaymentDetailsScreenConstants.padding
                )
            ).copy(color = theme.paymentFormThemeColors.paymentFormColors.background)
        )
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    public fun providePaymentDetailsHeader(theme: PaymentFormTheme): ScreenHeaderStyle {
        var screenHeaderStyle = DefaultLightStyle.screenHeader(
            textId = R.string.cko_payment_details_title,
            imageId = R.drawable.cko_ic_arrow_back
        )
        with(theme) {
            screenHeaderStyle = screenHeaderStyle.copy(
                textStyle = screenHeaderStyle.textStyle.copy(
                    color = paymentFormThemeColors.textColors.titleColor
                ),
                containerStyle = screenHeaderStyle.containerStyle.copy(
                    color = paymentFormThemeColors.paymentFormColors.background
                ),
                backIconStyle = screenHeaderStyle.backIconStyle.copy(
                    tinColor = paymentFormThemeColors.imageColors.tinColor
                )
            )
        }

        return screenHeaderStyle
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    public fun provideCardSchemeComponentStyle(theme: PaymentFormTheme): CardSchemeComponentStyle {
        var cardSchemeComponentStyle = DefaultLightStyle.cardSchemeComponentStyle()

        val textStyle =
            cardSchemeComponentStyle.titleStyle.textStyle
                .copy(color = theme.paymentFormThemeColors.textColors.titleColor)

        cardSchemeComponentStyle = cardSchemeComponentStyle.copy(
            titleStyle = cardSchemeComponentStyle.titleStyle.copy(textStyle = textStyle),

            )
        return cardSchemeComponentStyle
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    public fun provideCardNumberComponentStyle(theme: PaymentFormTheme): CardNumberComponentStyle {
        var cardNumberComponentStyle = DefaultCardNumberComponentStyle.light()
        val themeMapper = ThemeMapper(RequestThemeStyles(cardNumberComponentStyle.inputStyle)).map(theme)
        with(cardNumberComponentStyle) {
            val cardNumberInputFieldStyle =
                inputStyle.inputFieldStyle.copy(
                    textStyle = themeMapper.inputFieldTextStyle,
                    indicatorStyle = themeMapper.inputFieldIndicatorStyle,
                    containerStyle = themeMapper.inputFieldContainerStyle
                )

            cardNumberComponentStyle = copy(
                inputStyle = inputStyle.copy(
                    titleStyle = themeMapper.titleTextStyle?.let { inputStyle.titleStyle?.copy(textStyle = it) },
                    inputFieldStyle = cardNumberInputFieldStyle,
                    errorMessageStyle = themeMapper.errorMessageTextStyle?.let {
                        inputStyle.errorMessageStyle?.copy(
                            textStyle = it
                        )
                    }
                )
            )
        }

        return cardNumberComponentStyle
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    public fun provideExpiryDateComponentStyle(theme: PaymentFormTheme): ExpiryDateComponentStyle {
        var expiryDateComponentStyle = DefaultExpiryDateComponentStyle.light()
        val themeMapper = ThemeMapper(RequestThemeStyles(expiryDateComponentStyle.inputStyle)).map(theme)

        val expiryDateInputFieldStyle =
            expiryDateComponentStyle.inputStyle.inputFieldStyle.copy(
                textStyle = themeMapper.inputFieldTextStyle,
                indicatorStyle = themeMapper.inputFieldIndicatorStyle,
                containerStyle = themeMapper.inputFieldContainerStyle
            )

        expiryDateComponentStyle = expiryDateComponentStyle.copy(
            inputStyle = expiryDateComponentStyle.inputStyle.copy(
                titleStyle = themeMapper.titleTextStyle?.let {
                    expiryDateComponentStyle.inputStyle.titleStyle?.copy(
                        textStyle = it
                    )
                },
                subtitleStyle = themeMapper.subTitleTextStyle?.let {
                    expiryDateComponentStyle.inputStyle.subtitleStyle?.copy(
                        textStyle = it
                    )
                },
                inputFieldStyle = expiryDateInputFieldStyle,
                infoStyle = themeMapper.infoTextStyle?.let {
                    expiryDateComponentStyle.inputStyle.infoStyle?.copy(
                        textStyle = it
                    )
                },
                errorMessageStyle = themeMapper.errorMessageTextStyle?.let {
                    expiryDateComponentStyle.inputStyle.errorMessageStyle?.copy(
                        textStyle = it
                    )
                }
            )
        )

        return expiryDateComponentStyle
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    public fun provideCvvComponentStyle(theme: PaymentFormTheme): CvvComponentStyle {
        var cvvComponentStyle = DefaultCvvComponentStyle.light()
        val themeMapper = ThemeMapper(RequestThemeStyles(cvvComponentStyle.inputStyle)).map(theme)

        val cvvInputFieldStyle = cvvComponentStyle.inputStyle.inputFieldStyle.copy(
            textStyle = themeMapper.inputFieldTextStyle,
            indicatorStyle = themeMapper.inputFieldIndicatorStyle,
            containerStyle = themeMapper.inputFieldContainerStyle
        )

        cvvComponentStyle = cvvComponentStyle.copy(
            inputStyle = cvvComponentStyle.inputStyle.copy(
                titleStyle = themeMapper.titleTextStyle?.let {
                    cvvComponentStyle.inputStyle.titleStyle?.copy(textStyle = it)
                },
                subtitleStyle = themeMapper.subTitleTextStyle?.let {
                    cvvComponentStyle.inputStyle.subtitleStyle?.copy(
                        textStyle = it
                    )
                },
                inputFieldStyle = cvvInputFieldStyle,
                infoStyle = themeMapper.infoTextStyle?.let {
                    cvvComponentStyle.inputStyle.infoStyle?.copy(textStyle = it)
                },
                errorMessageStyle = themeMapper.errorMessageTextStyle?.let {
                    cvvComponentStyle.inputStyle.errorMessageStyle?.copy(
                        textStyle = it
                    )
                }
            )
        )
        return cvvComponentStyle
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    public fun provideAddressSummaryComponentStyle(theme: PaymentFormTheme): AddressSummaryComponentStyle {
        var addressSummaryComponentStyle = DefaultAddressSummaryComponentStyle.light()
        val themeMapper = ThemeMapper(RequestThemeStyles(null, addressSummaryComponentStyle)).map(theme)

        addressSummaryComponentStyle = addressSummaryComponentStyle.copy(
            titleStyle = themeMapper.titleTextStyle?.let {
                addressSummaryComponentStyle.titleStyle?.copy(textStyle = it)
            },
            subTitleStyle = themeMapper.subTitleTextStyle?.let {
                addressSummaryComponentStyle.subTitleStyle?.copy(textStyle = it)
            },
            containerStyle = addressSummaryComponentStyle.containerStyle
                .copy(color = theme.paymentFormThemeColors.paymentFormColors.background),
            summarySectionStyle = addressSummaryComponentStyle.summarySectionStyle.copy(
                editAddressButtonStyle = themeMapper.editAddressButtonStyle,
                addressTextStyle = addressSummaryComponentStyle.summarySectionStyle.addressTextStyle.copy(
                    textStyle = themeMapper.addressTextStyle ?: TextStyle()
                )
            ),
            addAddressButtonStyle = themeMapper.addAddressButtonStyle
        )

        return addressSummaryComponentStyle
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    public fun providePaymentButtonStyle(theme: PaymentFormTheme): PayButtonComponentStyle {
        var payButtonComponentStyle = DefaultPayButtonComponentStyle.light()
        val themeMapper = ThemeMapper(
            RequestThemeStyles(
                null,
                AddressSummaryComponentStyle(),
                payButtonComponentStyle.buttonStyle
            )
        ).map(theme)

        payButtonComponentStyle = payButtonComponentStyle.copy(buttonStyle = themeMapper.paymentDetailsButtonStyle)

        return payButtonComponentStyle
    }

    public fun buildPaymentDetailsStyle(
        headerStyle: ScreenHeaderStyle,
        cardSchemeComponentStyle: CardSchemeComponentStyle,
        cardNumberComponentStyle: CardNumberComponentStyle,
        expiryDateComponentStyle: ExpiryDateComponentStyle,
        payButtonComponentStyle: PayButtonComponentStyle,
        cvvComponentStyle: CvvComponentStyle? = null,
        addressSummaryComponentStyle: AddressSummaryComponentStyle? = null,
        fieldsComponentContainerStyle: ContainerStyle = ContainerStyle()
    ): PaymentDetailsStyle {
        return PaymentDetailsStyle().copy(
            paymentDetailsHeaderStyle = headerStyle,
            cardSchemeStyle = cardSchemeComponentStyle,
            cardNumberStyle = cardNumberComponentStyle,
            expiryDateStyle = expiryDateComponentStyle,
            cvvStyle = cvvComponentStyle,
            addressSummaryStyle = addressSummaryComponentStyle,
            payButtonStyle = payButtonComponentStyle,
            fieldsContainerStyle = fieldsComponentContainerStyle
        )
    }
}
