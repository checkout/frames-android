package com.checkout.frames.style.theme.paymentform

import androidx.annotation.RestrictTo
import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.checkout.frames.R
import com.checkout.frames.mapper.ThemeMapper
import com.checkout.frames.model.Padding
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingFormFields
import com.checkout.frames.style.component.CountryComponentStyle
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.InputFieldIndicatorStyle
import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.style.component.billingformdetails.HeaderComponentStyle
import com.checkout.frames.style.component.billingformdetails.InputComponentsContainerStyle
import com.checkout.frames.style.component.default.DefaultCountryComponentStyle
import com.checkout.frames.style.component.default.DefaultLightStyle
import com.checkout.frames.style.screen.BillingAddressDetailsStyle
import com.checkout.frames.style.screen.BillingFormStyle
import com.checkout.frames.style.screen.CountryPickerStyle
import com.checkout.frames.style.screen.default.DefaultBillingAddressDetailsStyle
import com.checkout.frames.style.screen.default.DefaultCountryPickerStyle
import com.checkout.frames.style.theme.PaymentFormTheme
import com.checkout.frames.style.theme.RequestThemeStyles
import com.checkout.frames.utils.constants.LightStyleConstants
import kotlin.collections.LinkedHashMap

public object BillingFormTheme {

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    public fun provideBillingAddressDetailsStyle(theme: PaymentFormTheme): BillingFormStyle {

        return buildBillingAddressDetailsStyle(
            headerStyle = provideBillingFormHeader(theme),
            containerStyle = ContainerStyle(color = theme.paymentFormThemeColors.paymentFormColors.background),
            inputComponentStyleValues = fetchInputComponentStyleValues(theme),
            countryStyle = provideCountryStyle(theme),
            countryPickerStyle = provideCountryPickerStyle(theme)
        )
    }

    private fun provideCountryPickerStyle(theme: PaymentFormTheme): CountryPickerStyle {
        var countryPickerStyle = DefaultCountryPickerStyle.light()

        with(theme) {
            countryPickerStyle = countryPickerStyle.copy(
                screenTitleStyle = countryPickerStyle.screenTitleStyle.copy(
                    textStyle = countryPickerStyle.screenTitleStyle.textStyle
                        .copy(color = paymentFormThemeColors.textColors.titleColor),
                    leadingIconStyle = countryPickerStyle.screenTitleStyle.leadingIconStyle?.copy(
                        tinColor = paymentFormThemeColors.imageColors.tinColor
                    )
                ),
                searchFieldStyle = countryPickerStyle.searchFieldStyle.copy(
                    textStyle = countryPickerStyle.searchFieldStyle.textStyle
                        .copy(color = paymentFormThemeColors.textColors.titleColor),
                    placeholderStyle = countryPickerStyle.searchFieldStyle.placeholderStyle.copy(
                        color = paymentFormThemeColors.textColors.titleColor
                    ),
                    indicatorStyle = InputFieldIndicatorStyle.Border().copy(
                        focusedBorderColor = paymentFormThemeColors.inputFieldColors.focusedBorderColor,
                        unfocusedBorderColor = paymentFormThemeColors.inputFieldColors.unfocusedBorderColor,
                        disabledBorderColor = paymentFormThemeColors.inputFieldColors.disabledBorderColor,
                        errorBorderColor = paymentFormThemeColors.inputFieldColors.errorBorderColor
                    ),
                    leadingIconStyle = countryPickerStyle.searchFieldStyle.leadingIconStyle?.copy(
                        tinColor = paymentFormThemeColors.imageColors.tinColor
                    ),
                    trailingIconStyle = countryPickerStyle.searchFieldStyle.trailingIconStyle?.copy(
                        tinColor = paymentFormThemeColors.imageColors.tinColor
                    ),
                    containerStyle = countryPickerStyle.searchFieldStyle.containerStyle.copy(
                        color = paymentFormThemeColors.inputFieldColors.inputFieldBackgroundColor
                    )
                ),
                searchItemStyle = countryPickerStyle.searchItemStyle.copy(
                    textStyle = countryPickerStyle.searchItemStyle.textStyle
                        .copy(color = theme.paymentFormThemeColors.textColors.titleColor)
                ),
                containerStyle = countryPickerStyle.containerStyle
                    .copy(color = paymentFormThemeColors.paymentFormColors.background)
            )
        }

        return countryPickerStyle
    }

    private fun provideCountryStyle(theme: PaymentFormTheme): CountryComponentStyle {
        var countryComponentStyle = DefaultCountryComponentStyle.light()
        val themeMapper = ThemeMapper(RequestThemeStyles(countryComponentStyle.inputStyle)).map(theme)
        val countryInputFieldStyle =
            countryComponentStyle.inputStyle.inputFieldStyle.copy(
                textStyle = themeMapper.inputFieldTextStyle,
                indicatorStyle = themeMapper.inputFieldIndicatorStyle,
                containerStyle = themeMapper.inputFieldContainerStyle,
                trailingIconStyle = countryComponentStyle.inputStyle.inputFieldStyle.trailingIconStyle?.copy(
                    tinColor = theme.paymentFormThemeColors.imageColors.tinColor
                )
            )

        countryComponentStyle = countryComponentStyle.copy(
            inputStyle = countryComponentStyle.inputStyle.copy(
                titleStyle = themeMapper.titleTextStyle?.let {
                    countryComponentStyle.inputStyle.titleStyle?.copy(
                        textStyle = it
                    )
                },
                subtitleStyle = themeMapper.subTitleTextStyle?.let {
                    countryComponentStyle.inputStyle.subtitleStyle?.copy(
                        textStyle = it
                    )
                },
                inputFieldStyle = countryInputFieldStyle,
                infoStyle = themeMapper.infoTextStyle?.let {
                    countryComponentStyle.inputStyle.infoStyle?.copy(
                        textStyle = it
                    )
                },
                errorMessageStyle = themeMapper.errorMessageTextStyle?.let {
                    countryComponentStyle.inputStyle.errorMessageStyle?.copy(
                        textStyle = it
                    )
                }
            )
        )

        return countryComponentStyle
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    public fun fetchInputComponentStyleValues(paymentFormTheme: PaymentFormTheme):
            LinkedHashMap<BillingFormFields, InputComponentStyle> {
        val inputComponentsStyles: LinkedHashMap<BillingFormFields, InputComponentStyle> = linkedMapOf()

        inputComponentsStyles[BillingFormFields.AddressLineOne] =
            provideInputStyle(
                theme = paymentFormTheme,
                isInputFieldHidden = false,
                isInputFieldOptional = false,
                titleTextId = R.string.cko_billing_form_input_field_address_line_one,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
        inputComponentsStyles[BillingFormFields.AddressLineTwo] = provideInputStyle(
            theme = paymentFormTheme,
            isInputFieldHidden = false,
            isInputFieldOptional = true,
            titleTextId = R.string.cko_billing_form_input_field_address_line_two,
            infoTextId = R.string.cko_input_field_optional_info,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        inputComponentsStyles[BillingFormFields.City] = provideInputStyle(
            theme = paymentFormTheme,
            isInputFieldHidden = false,
            isInputFieldOptional = false,
            titleTextId = R.string.cko_billing_form_input_field_city,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        inputComponentsStyles[BillingFormFields.State] = provideInputStyle(
            theme = paymentFormTheme,
            isInputFieldHidden = false,
            isInputFieldOptional = true,
            titleTextId = R.string.cko_billing_form_input_field_state,
            infoTextId = R.string.cko_input_field_optional_info,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        inputComponentsStyles[BillingFormFields.PostCode] = provideInputStyle(
            theme = paymentFormTheme,
            isInputFieldHidden = false,
            isInputFieldOptional = false,
            titleTextId = R.string.cko_billing_form_input_field_postcode,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        inputComponentsStyles[BillingFormFields.Phone] = provideInputStyle(
            theme = paymentFormTheme,
            isInputFieldHidden = false,
            isInputFieldOptional = false,
            titleTextId = R.string.cko_billing_form_input_field_phone_title,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done)
        )

        return inputComponentsStyles
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    private fun provideInputStyle(
        theme: PaymentFormTheme,
        isInputFieldHidden: Boolean = false,
        isInputFieldOptional: Boolean = false,
        @StringRes
        titleTextId: Int? = null,
        @StringRes
        subtitleTextId: Int? = null,
        @StringRes
        infoTextId: Int? = null,
        keyboardOptions: KeyboardOptions = KeyboardOptions.Default
    ): InputComponentStyle {
        var inputStyle = DefaultLightStyle.inputComponentStyle(
            titleTextId = titleTextId,
            subtitleTextId = subtitleTextId,
            infoTextId = infoTextId,
            padding = Padding(
                start = LightStyleConstants.inputComponentLeftPadding,
                end = LightStyleConstants.inputComponentRightPadding,
                bottom = LightStyleConstants.inputComponentBottomPadding
            ),
            keyboardOptions = keyboardOptions,
            isFieldOptional = isInputFieldOptional,
            isFieldHidden = isInputFieldHidden,
        )

        val themeMapper = ThemeMapper(RequestThemeStyles(inputStyle)).map(theme)
        val inputFieldStyle =
            inputStyle.inputFieldStyle.copy(
                textStyle = themeMapper.inputFieldTextStyle,
                indicatorStyle = themeMapper.inputFieldIndicatorStyle,
                containerStyle = themeMapper.inputFieldContainerStyle
            )

        inputStyle = inputStyle.copy(
            titleStyle = themeMapper.titleTextStyle?.let {
                inputStyle.titleStyle?.copy(
                    textStyle = it
                )
            },
            subtitleStyle = themeMapper.subTitleTextStyle?.let {
                inputStyle.subtitleStyle?.copy(
                    textStyle = it
                )
            },
            inputFieldStyle = inputFieldStyle,
            infoStyle = themeMapper.infoTextStyle?.let {
                inputStyle.infoStyle?.copy(
                    textStyle = it
                )
            },
            errorMessageStyle = themeMapper.errorMessageTextStyle?.let {
                inputStyle.errorMessageStyle?.copy(
                    textStyle = it
                )
            }

        )
        return inputStyle
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    public fun provideBillingFormHeader(theme: PaymentFormTheme): HeaderComponentStyle {
        var screenHeaderStyle = DefaultBillingAddressDetailsStyle.headerComponentStyle()
        with(theme) {
            val textStyle =
                screenHeaderStyle.headerTitleStyle.textStyle.copy(color = paymentFormThemeColors.textColors.titleColor)
            screenHeaderStyle = screenHeaderStyle.copy(
                headerTitleStyle = screenHeaderStyle.headerTitleStyle.copy(
                    textStyle = textStyle,
                    leadingIconStyle = screenHeaderStyle.headerTitleStyle.leadingIconStyle?.copy(
                        tinColor = paymentFormThemeColors.imageColors.tinColor
                    )
                ),
                headerButtonStyle = screenHeaderStyle.headerButtonStyle.copy(
                    contentColor = paymentFormThemeColors.buttonColors.contentColor,
                    disabledContentColor = paymentFormThemeColors.buttonColors.disabledContentColor,
                    containerColor = paymentFormThemeColors.buttonColors.containerColor,
                    disabledContainerColor = paymentFormThemeColors.buttonColors.disabledContainerColor,
                )
            )
        }

        return screenHeaderStyle
    }

    public fun buildBillingAddressDetailsStyle(
        headerStyle: HeaderComponentStyle = DefaultBillingAddressDetailsStyle.headerComponentStyle(),
        inputComponentStyleValues: LinkedHashMap<BillingFormFields, InputComponentStyle>,
        countryStyle: CountryComponentStyle = DefaultCountryComponentStyle.light(),
        containerStyle: ContainerStyle = ContainerStyle(),
        countryPickerStyle: CountryPickerStyle = DefaultCountryPickerStyle.light()
    ): BillingFormStyle {
        return BillingFormStyle(
            BillingAddressDetailsStyle(
                headerComponentStyle = headerStyle,
                inputComponentsContainerStyle = InputComponentsContainerStyle(inputComponentStyleValues),
                countryComponentStyle = countryStyle,
                containerStyle = containerStyle
            ),
            countryPickerStyle
        )
    }
}
