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
import com.checkout.frames.style.screen.BillingFormStyle
import com.checkout.frames.style.component.billingformdetails.HeaderComponentStyle
import com.checkout.frames.style.component.default.DefaultButtonStyle
import com.checkout.frames.style.screen.BillingAddressDetailsStyle
import com.checkout.frames.utils.constants.BillingAddressDetailsConstants
import com.checkout.frames.utils.constants.LightStyleConstants

public object DefaultBillingAddressDetailsStyle {
    @Suppress("LongMethod")
    public fun light(): BillingFormStyle {
        val billingFormHeaderTitleStyle = DefaultLightStyle.screenTitleTextLabelStyle(
            Padding(
                BillingAddressDetailsConstants.titlePaddingTop,
                BillingAddressDetailsConstants.titlePaddingBottom,
                BillingAddressDetailsConstants.titlePaddingStart,
                BillingAddressDetailsConstants.titlePaddingEnd
            )
        )

        val billingFormHeaderButtonStyle = DefaultButtonStyle.lightSolid(
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

        val inputComponentsStyles: LinkedHashMap<String, InputComponentStyle> = linkedMapOf()

        inputComponentsStyles[BillingFormFields.FullName.name] = DefaultLightStyle.inputComponentStyle(
            titleTextId = R.string.cko_billing_form_input_field_full_name,
            padding = Padding(
                top = LightStyleConstants.inputComponentTopPadding,
                start = LightStyleConstants.inputComponentLeftPadding,
                end = LightStyleConstants.inputComponentRightPadding,
                bottom = LightStyleConstants.inputComponentBottomPadding
            ),
            isFieldOptional = false
        )

        inputComponentsStyles[BillingFormFields.AddressLineOne.name] = DefaultLightStyle.inputComponentStyle(
            titleTextId = R.string.cko_billing_form_input_field_address_line_one,
            padding = Padding(
                start = LightStyleConstants.inputComponentLeftPadding,
                end = LightStyleConstants.inputComponentRightPadding,
                bottom = LightStyleConstants.inputComponentBottomPadding
            ),
            isFieldOptional = false
        )

        inputComponentsStyles[BillingFormFields.AddressLineTwo.name] = DefaultLightStyle.inputComponentStyle(
            titleTextId = R.string.cko_billing_form_input_field_address_line_two,

            padding = Padding(
                start = LightStyleConstants.inputComponentLeftPadding,
                end = LightStyleConstants.inputComponentRightPadding,
                bottom = LightStyleConstants.inputComponentBottomPadding
            ),
            isFieldOptional = true
        )

        inputComponentsStyles[BillingFormFields.City.name] = DefaultLightStyle.inputComponentStyle(
            titleTextId = R.string.cko_billing_form_input_field_city,
            padding = Padding(
                start = LightStyleConstants.inputComponentLeftPadding,
                end = LightStyleConstants.inputComponentRightPadding,
                bottom = LightStyleConstants.inputComponentBottomPadding
            ),
            isFieldOptional = false
        )

        inputComponentsStyles[BillingFormFields.State.name] = DefaultLightStyle.inputComponentStyle(
            titleTextId = R.string.cko_billing_form_input_field_state,
            infoTextId = R.string.cko_billing_form_input_field_info,
            padding = Padding(
                start = LightStyleConstants.inputComponentLeftPadding,
                end = LightStyleConstants.inputComponentRightPadding,
                bottom = LightStyleConstants.inputComponentBottomPadding
            ),
            isFieldOptional = true
        )

        inputComponentsStyles[BillingFormFields.PostCode.name] = DefaultLightStyle.inputComponentStyle(
            titleTextId = R.string.cko_billing_form_input_field_postcode,
            padding = Padding(
                start = LightStyleConstants.inputComponentLeftPadding,
                end = LightStyleConstants.inputComponentRightPadding,
                bottom = LightStyleConstants.inputComponentBottomPadding
            ),
            isFieldOptional = false
        )

        inputComponentsStyles[BillingFormFields.Country.name] = DefaultLightStyle.inputComponentStyle(
            titleTextId = R.string.cko_country_picker_screen_title,
            padding = Padding(
                start = LightStyleConstants.inputComponentLeftPadding,
                end = LightStyleConstants.inputComponentRightPadding,
                bottom = LightStyleConstants.inputComponentBottomPadding
            ),
            isFieldOptional = false
        )

        inputComponentsStyles[BillingFormFields.Phone.name] = DefaultLightStyle.inputComponentStyle(
            titleTextId = R.string.cko_billing_form_input_field_phone_title,
            subtitleTextId = R.string.cko_billing_form_input_field_phone_subtitle,
            padding = Padding(
                start = LightStyleConstants.inputComponentLeftPadding,
                end = LightStyleConstants.inputComponentRightPadding,
                bottom = LightStyleConstants.inputComponentBottomPadding
            ),
            isFieldOptional = false
        )

        val billingFormHeaderViewStyle = HeaderComponentStyle(
            billingFormHeaderTitleStyle,
            billingFormHeaderButtonStyle
        )

        val billingAddressDetailsStyle =
            BillingAddressDetailsStyle(
                headerComponentStyle = billingFormHeaderViewStyle,
                inputComponentsContainerStyle = InputComponentsContainerStyle(inputComponentsStyles)
            )

        return BillingFormStyle(billingAddressDetailsStyle)
    }
}
