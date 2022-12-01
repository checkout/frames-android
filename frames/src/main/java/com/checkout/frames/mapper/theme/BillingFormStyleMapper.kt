package com.checkout.frames.mapper.theme

import com.checkout.base.mapper.Mapper
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingFormFields
import com.checkout.frames.style.component.CountryComponentStyle
import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.style.component.base.TextLabelStyle
import com.checkout.frames.style.component.billingformdetails.HeaderComponentStyle
import com.checkout.frames.style.component.billingformdetails.InputComponentsContainerStyle
import com.checkout.frames.style.component.default.DefaultCountryComponentStyle
import com.checkout.frames.style.screen.BillingAddressDetailsStyle
import com.checkout.frames.style.screen.BillingFormStyle
import com.checkout.frames.style.screen.CountryPickerStyle
import com.checkout.frames.style.screen.default.DefaultBillingAddressDetailsStyle
import com.checkout.frames.style.screen.default.DefaultCountryPickerStyle
import com.checkout.frames.style.theme.PaymentFormComponent
import com.checkout.frames.style.theme.PaymentFormComponentField
import com.checkout.frames.style.theme.PaymentFormTheme
import com.checkout.frames.utils.extensions.provideContainerStyle
import com.checkout.frames.utils.extensions.provideInfoStyle
import com.checkout.frames.utils.extensions.provideErrorMessageStyle
import com.checkout.frames.utils.extensions.provideIndicatorStyle
import com.checkout.frames.utils.extensions.provideInputFieldContainerStyle
import com.checkout.frames.utils.extensions.provideSolidButtonStyle
import com.checkout.frames.utils.extensions.provideInputFieldStyle
import com.checkout.frames.utils.extensions.provideSubTitleStyle
import com.checkout.frames.utils.extensions.provideTitleStyle

internal class BillingFormStyleMapper : Mapper<PaymentFormTheme, BillingFormStyle> {

    override fun map(from: PaymentFormTheme) = BillingFormStyle(
        BillingAddressDetailsStyle(
            headerComponentStyle = provideHeaderComponentStyle(from),
            inputComponentsContainerStyle = provideInputComponentsContainerStyle(from),
            countryComponentStyle = provideCountryComponentStyle(from),
            containerStyle = from.provideContainerStyle()
        ),
        countryPickerStyle = provideCountryPickerStyle(from)
    )

    @Suppress("LongMethod")
    private fun provideInputComponentsContainerStyle(from: PaymentFormTheme): InputComponentsContainerStyle {
        var inputComponentsContainerStyle = DefaultBillingAddressDetailsStyle.inputComponentsContainerStyle()
        val inputComponentStyleValues = inputComponentsContainerStyle.inputComponentStyleValues

        val inputComponentsStyles: LinkedHashMap<BillingFormFields, InputComponentStyle> = linkedMapOf()

        val addressLineOneComponent = from.paymentFormComponents.find {
            PaymentFormComponentField.AddressLineOne.name == it.paymentFormComponentField.name
        }

        var addressLineOneInputStyle = inputComponentStyleValues[BillingFormFields.AddressLineOne]
        addressLineOneComponent?.let { component ->
            if (!component.isFieldHidden) {
                addressLineOneInputStyle = provideInputComponentStyle(addressLineOneInputStyle, component, from)
                inputComponentsStyles[BillingFormFields.AddressLineOne] =
                    addressLineOneInputStyle ?: InputComponentStyle()
            }
        }

        val addressLineTwoComponent = from.paymentFormComponents.find {
            PaymentFormComponentField.AddressLineTwo.name == it.paymentFormComponentField.name
        }
        var addressLineOTwoInputStyle = inputComponentStyleValues[BillingFormFields.AddressLineTwo]
        addressLineTwoComponent?.let { component ->
            if (!component.isFieldHidden) {
                addressLineOTwoInputStyle = provideInputComponentStyle(addressLineOTwoInputStyle, component, from)
                inputComponentsStyles[BillingFormFields.AddressLineTwo] =
                    addressLineOTwoInputStyle ?: InputComponentStyle()
            }
        }

        val cityComponent = from.paymentFormComponents.find {
            PaymentFormComponentField.City.name == it.paymentFormComponentField.name
        }
        var cityInputStyle = inputComponentStyleValues[BillingFormFields.City]
        cityComponent?.let { component ->
            if (!component.isFieldHidden) {
                cityInputStyle = provideInputComponentStyle(cityInputStyle, component, from)
                inputComponentsStyles[BillingFormFields.City] = cityInputStyle ?: InputComponentStyle()
            }
        }

        val stateComponent = from.paymentFormComponents.find {
            PaymentFormComponentField.State.name == it.paymentFormComponentField.name
        }
        var stateInputStyle = inputComponentStyleValues[BillingFormFields.State]
        stateComponent?.let { component ->
            if (!component.isFieldHidden) {
                stateInputStyle = provideInputComponentStyle(stateInputStyle, component, from)
                inputComponentsStyles[BillingFormFields.State] = stateInputStyle ?: InputComponentStyle()
            }
        }

        val postCodeComponent = from.paymentFormComponents.find {
            PaymentFormComponentField.PostCode.name == it.paymentFormComponentField.name
        }
        var postCodeInputStyle = inputComponentStyleValues[BillingFormFields.PostCode]
        postCodeComponent?.let { component ->
            if (!component.isFieldHidden) {
                postCodeInputStyle = provideInputComponentStyle(postCodeInputStyle, component, from)
                inputComponentsStyles[BillingFormFields.PostCode] = postCodeInputStyle ?: InputComponentStyle()
            }
        }

        val phoneComponent = from.paymentFormComponents.find {
            PaymentFormComponentField.Phone.name == it.paymentFormComponentField.name
        }
        var phoneInputStyle = inputComponentStyleValues[BillingFormFields.Phone]
        phoneComponent?.let { component ->
            phoneInputStyle = provideInputComponentStyle(phoneInputStyle, component, from)
            inputComponentsStyles[BillingFormFields.Phone] = phoneInputStyle ?: InputComponentStyle()
        }

        if (!inputComponentsStyles.isEmpty()) {
            inputComponentsContainerStyle = inputComponentsContainerStyle.copy(
                inputComponentStyleValues = inputComponentsStyles
            )
        }

        return inputComponentsContainerStyle
    }

    private fun provideInputComponentStyle(
        addressLineOneInputStyle: InputComponentStyle?,
        component: PaymentFormComponent,
        from: PaymentFormTheme
    ): InputComponentStyle? {
        return with(addressLineOneInputStyle) {
            this?.copy(
                titleStyle = titleStyle.provideTitleStyle(component, from),
                subtitleStyle = subtitleStyle.provideSubTitleStyle(component, from),
                infoStyle = infoStyle.provideInfoStyle(component, from),
                inputFieldStyle = this.provideInputFieldStyle(from),
                errorMessageStyle = errorMessageStyle.provideErrorMessageStyle(from),
                isInputFieldOptional = component.isFieldOptional
            )
        }
    }

    private fun provideCountryPickerStyle(from: PaymentFormTheme): CountryPickerStyle {
        var countryPickerStyle = DefaultCountryPickerStyle.light()
        val paymentFormComponent = from.paymentFormComponents.find {
            PaymentFormComponentField.CountryPicker.name == it.paymentFormComponentField.name
        }

        paymentFormComponent?.let { component ->
            with(from) {
                countryPickerStyle = with(countryPickerStyle) {
                    copy(
                        screenTitleStyle = screenTitleStyle.provideTitleStyle(component, from) ?: TextLabelStyle(),
                        searchFieldStyle = searchFieldStyle.copy(
                            textStyle = searchFieldStyle.textStyle
                                .copy(color = paymentFormThemeColors.textColors.titleColor),
                            placeholderStyle = searchFieldStyle.placeholderStyle.copy(
                                color = paymentFormThemeColors.textColors.subTitleColor
                            ),
                            indicatorStyle = from.provideIndicatorStyle(),
                            leadingIconStyle = searchFieldStyle.leadingIconStyle?.copy(
                                tinColor = paymentFormThemeColors.imageColors.tinColor
                            ),
                            trailingIconStyle = searchFieldStyle.trailingIconStyle?.copy(
                                tinColor = paymentFormThemeColors.imageColors.tinColor
                            ),
                            containerStyle = searchFieldStyle.containerStyle.provideInputFieldContainerStyle(from)
                        ),
                        searchItemStyle = searchItemStyle.provideTitleStyle(component, from) ?: TextLabelStyle(),
                        containerStyle = from.provideContainerStyle()
                    )
                }
            }
        }
        return countryPickerStyle
    }

    private fun provideCountryComponentStyle(from: PaymentFormTheme): CountryComponentStyle {
        var countryComponentStyle = DefaultCountryComponentStyle.light()
        val paymentFormComponent = from.paymentFormComponents.find {
            PaymentFormComponentField.Country.name == it.paymentFormComponentField.name
        }

        paymentFormComponent?.let { component ->
            countryComponentStyle = with(countryComponentStyle.inputStyle) {
                countryComponentStyle.copy(
                    inputStyle = copy(
                        titleStyle = titleStyle.provideTitleStyle(component, from),
                        subtitleStyle = subtitleStyle.provideSubTitleStyle(component, from),
                        inputFieldStyle = provideInputFieldStyle(from),
                        infoStyle = infoStyle.provideInfoStyle(component, from),
                        errorMessageStyle = errorMessageStyle.provideErrorMessageStyle(from)
                    )
                )
            }
        }

        return countryComponentStyle
    }

    private fun provideHeaderComponentStyle(from: PaymentFormTheme): HeaderComponentStyle {
        var screenHeaderStyle = DefaultBillingAddressDetailsStyle.headerComponentStyle()
        val headerComponent = from.paymentFormComponents.find {
            PaymentFormComponentField.BillingDetailsHeader.name == it.paymentFormComponentField.name
        }

        val headerButtonComponent = from.paymentFormComponents.find {
            PaymentFormComponentField.BillingDetailsHeaderButton.name == it.paymentFormComponentField.name
        }

        headerComponent?.let { component ->
            screenHeaderStyle = screenHeaderStyle.copy(
                headerTitleStyle = screenHeaderStyle.headerTitleStyle.provideTitleStyle(component, from)
                    ?: TextLabelStyle()
            )
        }

        headerButtonComponent?.let { component ->
            screenHeaderStyle = screenHeaderStyle.copy(
                headerButtonStyle = screenHeaderStyle.headerButtonStyle.provideSolidButtonStyle(from, component)
            )
        }

        return screenHeaderStyle
    }
}
