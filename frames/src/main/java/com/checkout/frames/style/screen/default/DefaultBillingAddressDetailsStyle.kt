package com.checkout.frames.style.screen.default

import com.checkout.frames.R
import com.checkout.frames.model.CornerRadius
import com.checkout.frames.model.Margin
import com.checkout.frames.model.Padding
import com.checkout.frames.model.Shape
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingFormFields
import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.style.component.billingformdetails.InputComponentsContainerStyle
import com.checkout.frames.style.component.default.DefaultLightStyle
import com.checkout.frames.style.component.billingformdetails.HeaderComponentStyle
import com.checkout.frames.style.component.default.DefaultButtonStyle
import com.checkout.frames.utils.constants.BillingAddressDetailsConstants
import com.checkout.frames.utils.constants.LightStyleConstants

public object DefaultBillingAddressDetailsStyle {

    @Suppress("LongMethod")
    public fun fetchInputComponentStyleValues(): LinkedHashMap<BillingFormFields, InputComponentStyle> {
        val inputComponentsStyles: LinkedHashMap<BillingFormFields, InputComponentStyle> = linkedMapOf()

        inputComponentsStyles[BillingFormFields.FullName] = DefaultLightStyle.inputComponentStyle(
            titleTextId = R.string.cko_billing_form_input_field_full_name,
            padding = Padding(
                top = LightStyleConstants.inputComponentTopPadding,
                start = LightStyleConstants.inputComponentLeftPadding,
                end = LightStyleConstants.inputComponentRightPadding,
                bottom = LightStyleConstants.inputComponentBottomPadding
            ),
            isFieldOptional = false
        )

        inputComponentsStyles[BillingFormFields.AddressLineOne] = DefaultLightStyle.inputComponentStyle(
            titleTextId = R.string.cko_billing_form_input_field_address_line_one,
            padding = Padding(
                start = LightStyleConstants.inputComponentLeftPadding,
                end = LightStyleConstants.inputComponentRightPadding,
                bottom = LightStyleConstants.inputComponentBottomPadding
            ),
            isFieldOptional = false
        )

        inputComponentsStyles[BillingFormFields.AddressLineTwo] = DefaultLightStyle.inputComponentStyle(
            titleTextId = R.string.cko_billing_form_input_field_address_line_two,

            padding = Padding(
                start = LightStyleConstants.inputComponentLeftPadding,
                end = LightStyleConstants.inputComponentRightPadding,
                bottom = LightStyleConstants.inputComponentBottomPadding
            ),
            isFieldOptional = true
        )

        inputComponentsStyles[BillingFormFields.City] = DefaultLightStyle.inputComponentStyle(
            titleTextId = R.string.cko_billing_form_input_field_city,
            padding = Padding(
                start = LightStyleConstants.inputComponentLeftPadding,
                end = LightStyleConstants.inputComponentRightPadding,
                bottom = LightStyleConstants.inputComponentBottomPadding
            ),
            isFieldOptional = false
        )

        inputComponentsStyles[BillingFormFields.State] = DefaultLightStyle.inputComponentStyle(
            titleTextId = R.string.cko_billing_form_input_field_state,
            infoTextId = R.string.cko_billing_form_input_field_info,
            padding = Padding(
                start = LightStyleConstants.inputComponentLeftPadding,
                end = LightStyleConstants.inputComponentRightPadding,
                bottom = LightStyleConstants.inputComponentBottomPadding
            ),
            isFieldOptional = true
        )

        inputComponentsStyles[BillingFormFields.PostCode] = DefaultLightStyle.inputComponentStyle(
            titleTextId = R.string.cko_billing_form_input_field_postcode,
            padding = Padding(
                start = LightStyleConstants.inputComponentLeftPadding,
                end = LightStyleConstants.inputComponentRightPadding,
                bottom = LightStyleConstants.inputComponentBottomPadding
            ),
            isFieldOptional = false
        )

        inputComponentsStyles[BillingFormFields.Country] = DefaultLightStyle.inputComponentStyle(
            titleTextId = R.string.cko_country_picker_screen_title,
            padding = Padding(
                start = LightStyleConstants.inputComponentLeftPadding,
                end = LightStyleConstants.inputComponentRightPadding,
                bottom = LightStyleConstants.inputComponentBottomPadding
            ),
            isFieldOptional = false
        )

        inputComponentsStyles[BillingFormFields.Phone] = DefaultLightStyle.inputComponentStyle(
            titleTextId = R.string.cko_billing_form_input_field_phone_title,
            subtitleTextId = R.string.cko_billing_form_input_field_phone_subtitle,
            padding = Padding(
                start = LightStyleConstants.inputComponentLeftPadding,
                end = LightStyleConstants.inputComponentRightPadding,
                bottom = LightStyleConstants.inputComponentBottomPadding
            ),
            isFieldOptional = false
        )

        return inputComponentsStyles
    }

    public fun headerComponentStyle(): HeaderComponentStyle = HeaderComponentStyle(
        DefaultLightStyle.screenTitleTextLabelStyle(
            Padding(
                BillingAddressDetailsConstants.titlePaddingTop,
                BillingAddressDetailsConstants.titlePaddingBottom,
                BillingAddressDetailsConstants.titlePaddingStart,
                BillingAddressDetailsConstants.titlePaddingEnd
            )
        ),
        DefaultButtonStyle.lightSolid(
            containerColor = BillingAddressDetailsConstants.buttonContainerColor,
            disabledContainerColor = BillingAddressDetailsConstants.buttonDisabledContainerColor,
            contentColor = BillingAddressDetailsConstants.buttonContentColor,
            disabledContentColor = BillingAddressDetailsConstants.buttonDisabledContentColor,
            shape = Shape.RoundCorner,
            cornerRadius = CornerRadius(BillingAddressDetailsConstants.buttonDefaultCornerRadius),
            contentPadding = Padding(
                start = BillingAddressDetailsConstants.buttonContentStartPadding,
                top = BillingAddressDetailsConstants.buttonContentTopPadding,
                bottom = BillingAddressDetailsConstants.buttonContentBottomPadding,
                end = BillingAddressDetailsConstants.buttonContentEndPadding
            ),
            margin = Margin(
                top = BillingAddressDetailsConstants.buttonContainerTopPadding,
                end = BillingAddressDetailsConstants.buttonContainerEndPadding
            )
        )
    )

    public fun inputComponentsContainerStyle(): InputComponentsContainerStyle = InputComponentsContainerStyle(
        fetchInputComponentStyleValues()
    )
}
