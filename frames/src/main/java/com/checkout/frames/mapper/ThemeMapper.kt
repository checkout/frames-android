package com.checkout.frames.mapper

import com.checkout.base.mapper.Mapper
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.InputFieldIndicatorStyle
import com.checkout.frames.style.component.base.TextStyle
import com.checkout.frames.style.theme.PaymentFormTheme
import com.checkout.frames.style.theme.PaymentFormThemeStyle
import com.checkout.frames.style.theme.RequestThemeStyles

internal class ThemeMapper(private val requestThemeStyles: RequestThemeStyles) :
    Mapper<PaymentFormTheme, PaymentFormThemeStyle> {

    override fun map(from: PaymentFormTheme): PaymentFormThemeStyle {
        return with(requestThemeStyles.inputComponentStyle) {
            PaymentFormThemeStyle(
                titleTextStyle = provideTitleTextStyle(from),
                subTitleTextStyle = provideSubTitleTextStyle(from),
                infoTextStyle = this?.infoStyle?.textStyle?.copy(
                    color = from.paymentFormThemeColors.textColors.infoColor
                ),
                inputFieldTextStyle = this?.inputFieldStyle?.textStyle?.copy(
                    color = from.paymentFormThemeColors.textColors.titleColor
                ) ?: TextStyle(),

                errorMessageTextStyle = this?.errorMessageStyle?.textStyle
                    ?.copy(color = from.paymentFormThemeColors.textColors.errorColor),
                addressTextStyle = requestThemeStyles.addressSummaryComponentStyle.summarySectionStyle.addressTextStyle
                    .textStyle.copy(color = from.paymentFormThemeColors.textColors.titleColor),
                inputFieldIndicatorStyle = InputFieldIndicatorStyle.Border().copy(
                    focusedBorderColor = from.paymentFormThemeColors.inputFieldColors.focusedBorderColor,
                    unfocusedBorderColor = from.paymentFormThemeColors.inputFieldColors.unfocusedBorderColor,
                    disabledBorderColor = from.paymentFormThemeColors.inputFieldColors.disabledBorderColor,
                    errorBorderColor = from.paymentFormThemeColors.inputFieldColors.errorBorderColor
                ),
                addAddressButtonStyle = requestThemeStyles.addressSummaryComponentStyle.addAddressButtonStyle.copy(
                    contentColor = from.paymentFormThemeColors.buttonColors.contentColor,
                    disabledContentColor = from.paymentFormThemeColors.buttonColors.disabledContentColor,
                    containerColor = from.paymentFormThemeColors.buttonColors.containerColor,
                    disabledContainerColor = from.paymentFormThemeColors.buttonColors.disabledContainerColor,
                    textStyle = requestThemeStyles.addressSummaryComponentStyle.addAddressButtonStyle.textStyle.apply {
                        trailingIconStyle = this.trailingIconStyle
                            ?.copy(tinColor = from.paymentFormThemeColors.buttonColors.contentColor)
                    }
                ),
                editAddressButtonStyle = with(
                    requestThemeStyles.addressSummaryComponentStyle.summarySectionStyle.editAddressButtonStyle
                ) {
                    copy(
                        contentColor = from.paymentFormThemeColors.buttonColors.contentColor,
                        disabledContentColor = from.paymentFormThemeColors.buttonColors.disabledContentColor,
                        containerColor = from.paymentFormThemeColors.buttonColors.containerColor,
                        disabledContainerColor = from.paymentFormThemeColors.buttonColors.disabledContainerColor,
                        textStyle = textStyle.apply {
                            trailingIconStyle = trailingIconStyle
                                ?.copy(tinColor = from.paymentFormThemeColors.buttonColors.contentColor)
                        }
                    )
                },
                paymentDetailsButtonStyle = requestThemeStyles.buttonStyle.copy(
                    contentColor = from.paymentFormThemeColors.buttonColors.contentColor,
                    disabledContentColor = from.paymentFormThemeColors.buttonColors.disabledContentColor,
                    containerColor = from.paymentFormThemeColors.buttonColors.containerColor,
                    disabledContainerColor = from.paymentFormThemeColors.buttonColors.disabledContainerColor,
                ),
                inputFieldContainerStyle = this?.inputFieldStyle?.containerStyle?.copy(
                    color = from.paymentFormThemeColors.inputFieldColors.inputFieldBackgroundColor
                ) ?: ContainerStyle(),
                containerStyle = ContainerStyle().copy(color = from.paymentFormThemeColors.paymentFormColors.background)
            )
        }
    }

    private fun provideSubTitleTextStyle(from: PaymentFormTheme): TextStyle? {
        return with(requestThemeStyles.inputComponentStyle) {
            if (this == null) requestThemeStyles.addressSummaryComponentStyle.subTitleStyle?.textStyle?.copy(
                color = from.paymentFormThemeColors.textColors.titleColor
            ) else this.subtitleStyle?.textStyle?.copy(color = from.paymentFormThemeColors.textColors.subTitleColor)
        }
    }

    private fun provideTitleTextStyle(from: PaymentFormTheme): TextStyle? {
        return with(requestThemeStyles.inputComponentStyle) {
            if (this == null) requestThemeStyles.addressSummaryComponentStyle.titleStyle?.textStyle?.copy(
                color = from.paymentFormThemeColors.textColors.titleColor
            ) else this.titleStyle?.textStyle?.copy(color = from.paymentFormThemeColors.textColors.titleColor)
        }
    }
}
