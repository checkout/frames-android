package com.checkout.example.frames.paymentformstyling

import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.checkout.example.frames.paymentformstyling.PaymentFormConstants.backgroundColor
import com.checkout.example.frames.paymentformstyling.PaymentFormConstants.cornerRadius
import com.checkout.example.frames.paymentformstyling.PaymentFormConstants.inputFieldBorderShape
import com.checkout.example.frames.paymentformstyling.PaymentFormConstants.inputFieldColor
import com.checkout.example.frames.paymentformstyling.PaymentFormConstants.inputFieldCornerRadius
import com.checkout.example.frames.paymentformstyling.PaymentFormConstants.paddingSixDp
import com.checkout.example.frames.paymentformstyling.PaymentFormConstants.paddingTenDp
import com.checkout.example.frames.paymentformstyling.PaymentFormConstants.textColor
import com.checkout.frames.R
import com.checkout.frames.model.Margin
import com.checkout.frames.model.Padding
import com.checkout.frames.model.Shape
import com.checkout.frames.model.font.FontWeight
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingFormFields
import com.checkout.frames.style.component.CountryComponentStyle
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.style.component.base.InputFieldIndicatorStyle
import com.checkout.frames.style.component.base.InputFieldStyle
import com.checkout.frames.style.component.base.TextStyle
import com.checkout.frames.style.component.billingformdetails.HeaderComponentStyle
import com.checkout.frames.style.component.billingformdetails.InputComponentsContainerStyle
import com.checkout.frames.style.component.default.DefaultCountryComponentStyle
import com.checkout.frames.style.component.default.DefaultLightStyle
import com.checkout.frames.style.screen.BillingAddressDetailsStyle
import com.checkout.frames.style.screen.BillingFormStyle
import com.checkout.frames.style.screen.CountryPickerStyle
import com.checkout.frames.style.screen.default.DefaultBillingAddressDetailsStyle
import com.checkout.frames.style.screen.default.DefaultCountryPickerStyle
import com.checkout.frames.utils.constants.CountryPickerScreenConstants
import com.checkout.frames.utils.constants.LightStyleConstants

object CustomBillingFormStyle {
    private val defaultKeyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)

    fun provideBillingFormStyle() = BillingFormStyle(
        billingAddressDetailsStyle = provideBillingAddressDetailsStyle(),
        countryPickerStyle = provideCountryPickerStyle(),
    )

    private fun provideCountryPickerStyle(): CountryPickerStyle {
        var style = DefaultCountryPickerStyle.light()

        style = style.copy(
            screenTitleStyle = style.screenTitleStyle.copy(
                textStyle = style.screenTitleStyle.textStyle.copy(color = textColor),
                leadingIconStyle = style.screenTitleStyle.leadingIconStyle?.copy(tinColor = textColor),
            ),
            containerStyle = style.containerStyle.copy(
                color = backgroundColor,
            ),
            searchFieldStyle = with(style.searchFieldStyle) {
                copy(
                    containerStyle = provideContainerStyle().copy(
                        margin = Margin(
                            start = CountryPickerScreenConstants.margin,
                            end = CountryPickerScreenConstants.margin,
                            bottom = CountryPickerScreenConstants.margin,
                        ),
                    ),
                    indicatorStyle = provideIndicatorStyle(),
                    placeholderStyle = placeholderStyle.copy(color = PaymentFormConstants.placeHolderTextColor),
                    leadingIconStyle = leadingIconStyle?.copy(
                        tinColor = textColor,
                    ),
                    trailingIconStyle = trailingIconStyle?.copy(
                        tinColor = textColor,
                    ),
                )
            },
            searchItemStyle = style.searchItemStyle.copy(
                textStyle = style.searchItemStyle.textStyle.copy(
                    color = textColor,
                ),
            ),
        )
        return style
    }

    private fun provideBillingAddressDetailsStyle() = BillingAddressDetailsStyle(
        headerComponentStyle = provideHeaderComponentStyle(),
        inputComponentsContainerStyle = InputComponentsContainerStyle(fetchInputComponentStyleValues()),
        countryComponentStyle = provideCountryComponentStyle(),
        containerStyle = ContainerStyle(color = backgroundColor),
    )

    private fun provideCountryComponentStyle(): CountryComponentStyle {
        var style = DefaultCountryComponentStyle.light()
        var inputStyle = style.inputStyle

        inputStyle = inputStyle.copy(
            inputFieldStyle = provideInputFieldStyle(inputStyle.inputFieldStyle),
            titleStyle = inputStyle.titleStyle?.copy(
                textStyle = inputStyle.titleStyle?.textStyle?.copy(color = textColor) ?: TextStyle(),
                containerStyle = ContainerStyle(padding = Padding(start = cornerRadius, bottom = paddingTenDp)),
            ),
        )

        style = style.copy(
            inputStyle = inputStyle,
        )

        return style
    }

    private fun provideHeaderComponentStyle(): HeaderComponentStyle {
        var style = DefaultBillingAddressDetailsStyle.headerComponentStyle()
        style = style.copy(
            headerTitleStyle = style.headerTitleStyle.copy(
                textStyle = style.headerTitleStyle.textStyle.copy(
                    color = textColor, fontWeight = FontWeight.Bold,
                ),
            ),
            headerButtonStyle = style.headerButtonStyle
                .copy(
                    contentColor = inputFieldColor,
                    containerColor = textColor,
                    shape = Shape.Circle,
                    cornerRadius = inputFieldCornerRadius,
                ),
        )

        return style
    }

    @Suppress("LongMethod")
    private fun fetchInputComponentStyleValues(): LinkedHashMap<BillingFormFields, InputComponentStyle> {
        val inputComponentsStyles: LinkedHashMap<BillingFormFields, InputComponentStyle> = linkedMapOf()

        inputComponentsStyles[BillingFormFields.CardHolderName] = provideInputComponentStyle(
            placeholderTextId = R.string.cko_card_holder_name_title,
            isFieldOptional = true,
            infoTextId = R.string.cko_input_field_optional_info,
            keyboardOptions = defaultKeyboardOptions,
            padding = Padding(
                start = LightStyleConstants.inputComponentLeftPadding,
                end = LightStyleConstants.inputComponentRightPadding,
                bottom = LightStyleConstants.inputComponentBottomPadding,
            ),
        )

        inputComponentsStyles[BillingFormFields.AddressLineOne] = provideInputComponentStyle(
            placeholderTextId = R.string.cko_billing_form_input_field_address_line_one,
            isFieldOptional = false,
            keyboardOptions = defaultKeyboardOptions,
            padding = Padding(
                start = LightStyleConstants.inputComponentLeftPadding,
                end = LightStyleConstants.inputComponentRightPadding,
            ),
        )

        inputComponentsStyles[BillingFormFields.AddressLineTwo] = provideInputComponentStyle(
            placeholderTextId = R.string.cko_billing_form_input_field_address_line_two,
            infoTextId = R.string.cko_input_field_optional_info,
            isFieldOptional = true,
            keyboardOptions = defaultKeyboardOptions,
            padding = Padding(
                start = LightStyleConstants.inputComponentLeftPadding,
                end = LightStyleConstants.inputComponentRightPadding,
                bottom = LightStyleConstants.inputComponentBottomPadding,
            ),
        )

        inputComponentsStyles[BillingFormFields.City] = provideInputComponentStyle(
            placeholderTextId = R.string.cko_billing_form_input_field_city,
            isFieldOptional = false,
            keyboardOptions = defaultKeyboardOptions,
            padding = Padding(
                start = LightStyleConstants.inputComponentLeftPadding,
                end = LightStyleConstants.inputComponentRightPadding,
            ),
        )

        inputComponentsStyles[BillingFormFields.State] = provideInputComponentStyle(
            placeholderTextId = R.string.cko_billing_form_input_field_state,
            infoTextId = R.string.cko_input_field_optional_info,
            isFieldOptional = true,
            keyboardOptions = defaultKeyboardOptions,
            padding = Padding(
                start = LightStyleConstants.inputComponentLeftPadding,
                end = LightStyleConstants.inputComponentRightPadding,
                bottom = LightStyleConstants.inputComponentBottomPadding,
            ),
        )

        inputComponentsStyles[BillingFormFields.PostCode] = provideInputComponentStyle(
            placeholderTextId = R.string.cko_billing_form_input_field_postcode,
            isFieldOptional = false,
            keyboardOptions = defaultKeyboardOptions,
            padding = Padding(
                start = LightStyleConstants.inputComponentLeftPadding,
                end = LightStyleConstants.inputComponentRightPadding,
                bottom = LightStyleConstants.inputComponentBottomPadding,
            ),
        )

        inputComponentsStyles[BillingFormFields.Phone] = provideInputComponentStyle(
            placeholderTextId = R.string.cko_billing_form_input_field_phone_title,
            isFieldOptional = true,
            infoTextId = R.string.cko_input_field_optional_info,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            padding = Padding(
                start = LightStyleConstants.inputComponentLeftPadding,
                end = LightStyleConstants.inputComponentRightPadding,
                bottom = paddingTenDp,
            ),
        )

        inputComponentsStyles[BillingFormFields.Country] = DefaultLightStyle.inputComponentStyle(
            titleTextId = R.string.cko_country_picker_screen_title,
            isFieldOptional = true,
            infoTextId = R.string.cko_input_field_optional_info,
            padding = Padding(
                start = LightStyleConstants.inputComponentLeftPadding,
                end = LightStyleConstants.inputComponentRightPadding,
                bottom = LightStyleConstants.inputComponentBottomPadding,
            ),
        )

        return inputComponentsStyles
    }

    private fun provideInputComponentStyle(
        @StringRes placeholderTextId: Int? = null,
        @StringRes infoTextId: Int? = null,
        isFieldOptional: Boolean = false,
        keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
        padding: Padding = Padding(),
    ): InputComponentStyle {
        var style = DefaultLightStyle.inputComponentStyle(
            placeholderResourceTextId = placeholderTextId,
            infoTextId = infoTextId,
            padding = padding,
            isFieldOptional = isFieldOptional,
            keyboardOptions = keyboardOptions,
        )

        style = style.copy(
            infoStyle = style.infoStyle?.copy(
                containerStyle = ContainerStyle(
                    padding = Padding(
                        top = paddingSixDp, bottom = paddingSixDp, end = cornerRadius,
                    ),
                ),
            ),
            inputFieldStyle = provideInputFieldStyle(style.inputFieldStyle),
        )

        return style
    }

    private fun provideInputFieldStyle(inputFieldStyle: InputFieldStyle): InputFieldStyle {
        return inputFieldStyle.copy(
            containerStyle = provideContainerStyle(),
            indicatorStyle = provideIndicatorStyle(),
            placeholderStyle = inputFieldStyle.placeholderStyle.copy(
                color = PaymentFormConstants.placeHolderTextColor,
            ),
            trailingIconStyle = inputFieldStyle.trailingIconStyle?.copy(
                tinColor = textColor,
            ),
        )
    }

    private fun provideIndicatorStyle(): InputFieldIndicatorStyle = InputFieldIndicatorStyle.Underline(
        focusedUnderlineThickness = 0,
        unfocusedUnderlineThickness = 0,
    )

    private fun provideContainerStyle(): ContainerStyle {
        return ContainerStyle(
            shape = inputFieldBorderShape,
            color = inputFieldColor,
            cornerRadius = inputFieldCornerRadius,
        )
    }
}
