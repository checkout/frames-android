package com.checkout.frames.style.screen.default

import com.checkout.frames.R
import com.checkout.frames.model.CornerRadius
import com.checkout.frames.model.Margin
import com.checkout.frames.model.Padding
import com.checkout.frames.model.Shape
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingFormFields
import com.checkout.frames.style.component.billingformdetails.BillingAddressInputComponentsContainerStyle
import com.checkout.frames.style.component.default.DefaultLightStyle
import com.checkout.frames.style.screen.BillingFormStyle
import com.checkout.frames.style.component.billingformdetails.BillingAddressHeaderComponentStyle
import com.checkout.frames.style.component.billingformdetails.BillingAddressInputComponentStyle
import com.checkout.frames.style.component.default.DefaultButtonStyle
import com.checkout.frames.style.screen.BillingAddressDetailsStyle
import com.checkout.frames.utils.constants.ButtonConstants
import com.checkout.frames.utils.constants.CountryPickerScreenConstants
import com.checkout.frames.utils.constants.LightStyleConstants

public object DefaultBillingFormStyle {

    @Suppress("LongMethod")
    public fun light(): BillingFormStyle {
        val billingFormHeaderTitleStyle = DefaultLightStyle.screenTitleTextLabelStyle(
            Padding(
                CountryPickerScreenConstants.titlePaddingTop,
                CountryPickerScreenConstants.titlePaddingBottom,
                CountryPickerScreenConstants.titlePaddingStart,
                CountryPickerScreenConstants.titlePaddingEnd
            )
        )

        val billingFormHeaderButtonStyle = DefaultButtonStyle.lightSolid(
            containerColor = ButtonConstants.containerColor,
            disabledContainerColor = ButtonConstants.disabledContentColor,
            contentColor = ButtonConstants.contentColor,
            disabledContentColor = ButtonConstants.disabledContentColor,
            shape = Shape.RoundCorner,
            cornerRadius = CornerRadius(ButtonConstants.defaultCornerRadius),
            contentPadding = Padding(
                start = ButtonConstants.buttonContentStartPadding,
                top = ButtonConstants.buttonContentTopPadding,
                bottom = ButtonConstants.buttonContentBottomPadding,
                end = ButtonConstants.buttonContentEndPadding
            ),
            margin = Margin(
                top = ButtonConstants.buttonContainerTopPadding,
                end = ButtonConstants.buttonContainerEndPadding
            )
        )

        val billingAddressInputComponentsContainerStyle = BillingAddressInputComponentsContainerStyle(
            listOf(
                BillingAddressInputComponentStyle(
                    BillingFormFields.FullName.name,
                    isOptional = false,
                    DefaultLightStyle.inputComponentStyle(
                        titleTextId = R.string.cko_billing_form_input_field_full_name,
                        padding = Padding(
                            top = LightStyleConstants.inputComponentTopPadding,
                            start = LightStyleConstants.inputComponentLeftPadding,
                            end = LightStyleConstants.inputComponentRightPadding,
                            bottom = LightStyleConstants.inputComponentBottomPadding

                        )
                    )
                ),
                BillingAddressInputComponentStyle(
                    BillingFormFields.AddressLineOne.name,
                    isOptional = false,
                    DefaultLightStyle.inputComponentStyle(
                        titleTextId = R.string.cko_billing_form_input_field_address_line_one,
                        padding = Padding(
                            start = LightStyleConstants.inputComponentLeftPadding,
                            end = LightStyleConstants.inputComponentRightPadding,
                            bottom = LightStyleConstants.inputComponentBottomPadding
                        )
                    )
                ),
                BillingAddressInputComponentStyle(
                    BillingFormFields.AddressLineTwo.name,
                    isOptional = true,
                    DefaultLightStyle.inputComponentStyle(
                        titleTextId = R.string.cko_billing_form_input_field_address_line_two,
                        infoTextId = R.string.cko_billing_form_input_field_info,
                        padding = Padding(
                            start = LightStyleConstants.inputComponentLeftPadding,
                            end = LightStyleConstants.inputComponentRightPadding,
                            bottom = LightStyleConstants.inputComponentBottomPadding
                        )
                    )
                ),
                BillingAddressInputComponentStyle(
                    BillingFormFields.City.name,
                    isOptional = false,
                    DefaultLightStyle.inputComponentStyle(
                        titleTextId = R.string.cko_billing_form_input_field_city,
                        padding = Padding(
                            start = LightStyleConstants.inputComponentLeftPadding,
                            end = LightStyleConstants.inputComponentRightPadding,
                            bottom = LightStyleConstants.inputComponentBottomPadding
                        )
                    )
                ),
                BillingAddressInputComponentStyle(
                    BillingFormFields.State.name,
                    isOptional = true,
                    DefaultLightStyle.inputComponentStyle(
                        titleTextId = R.string.cko_billing_form_input_field_state,
                        infoTextId = R.string.cko_billing_form_input_field_info,
                        padding = Padding(
                            start = LightStyleConstants.inputComponentLeftPadding,
                            end = LightStyleConstants.inputComponentRightPadding,
                            bottom = LightStyleConstants.inputComponentBottomPadding
                        )
                    )
                ),
                BillingAddressInputComponentStyle(
                    BillingFormFields.PostCode.name,
                    isOptional = false,
                    DefaultLightStyle.inputComponentStyle(
                        titleTextId = R.string.cko_billing_form_input_field_postcode,
                        padding = Padding(
                            start = LightStyleConstants.inputComponentLeftPadding,
                            end = LightStyleConstants.inputComponentRightPadding,
                            bottom = LightStyleConstants.inputComponentBottomPadding
                        )
                    )
                ),
                BillingAddressInputComponentStyle(
                    BillingFormFields.Country.name,
                    isOptional = false,
                    DefaultLightStyle.inputComponentStyle(
                        titleTextId = R.string.cko_country_picker_screen_title,
                        padding = Padding(
                            start = LightStyleConstants.inputComponentLeftPadding,
                            end = LightStyleConstants.inputComponentRightPadding,
                            bottom = LightStyleConstants.inputComponentBottomPadding
                        )
                    )
                ),
                BillingAddressInputComponentStyle(
                    BillingFormFields.PhoneNumber.name,
                    isOptional = false,
                    DefaultLightStyle.inputComponentStyle(
                        titleTextId = R.string.cko_billing_form_input_field_phone_title,
                        subtitleTextId = R.string.cko_billing_form_input_field_phone_subtitle,
                        padding = Padding(
                            start = LightStyleConstants.inputComponentLeftPadding,
                            end = LightStyleConstants.inputComponentRightPadding,
                            bottom = LightStyleConstants.inputComponentBottomPadding
                        )
                    )
                )
            )
        )

        val billingFormHeaderViewStyle = BillingAddressHeaderComponentStyle(
            billingFormHeaderTitleStyle,
            billingFormHeaderButtonStyle
        )

        val billingAddressDetailsStyle =
            BillingAddressDetailsStyle(billingFormHeaderViewStyle, billingAddressInputComponentsContainerStyle)

        return BillingFormStyle(billingAddressDetailsStyle)
    }
}
