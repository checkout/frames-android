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
        var inputComponentsContainerStyle = DefaultBillingAddressDetailsStyle.inputComponentsContainerStyle(
            isRequestedCardHolderName = true
        )
        val defaultComponentStylesValues = inputComponentsContainerStyle.inputComponentStyleValues

        val componentStylesValues: LinkedHashMap<BillingFormFields, InputComponentStyle> = linkedMapOf()

        val cardHolderNameComponent = from.paymentFormComponents.find {
            PaymentFormComponentField.BillingFormCardHolderName.name == it.paymentFormComponentField.name
        }
        val cardHolderNameInputStyle = defaultComponentStylesValues[BillingFormFields.CardHolderName]
        cardHolderNameComponent?.let { component ->
            provideComponentStyle(component, cardHolderNameInputStyle, from)?.let { inputComponentStyle ->
                componentStylesValues[BillingFormFields.CardHolderName] = inputComponentStyle
            }
        }

        val addressLineOneComponent = from.paymentFormComponents.find {
            PaymentFormComponentField.AddressLineOne.name == it.paymentFormComponentField.name
        }
        val addressLineOneInputStyle = defaultComponentStylesValues[BillingFormFields.AddressLineOne]
        addressLineOneComponent?.let { component ->
            provideComponentStyle(component, addressLineOneInputStyle, from)?.let { inputComponentStyle ->
                componentStylesValues[BillingFormFields.AddressLineOne] = inputComponentStyle
            }
        }

        val addressLineTwoComponent = from.paymentFormComponents.find {
            PaymentFormComponentField.AddressLineTwo.name == it.paymentFormComponentField.name
        }
        val addressLineOTwoInputStyle = defaultComponentStylesValues[BillingFormFields.AddressLineTwo]
        addressLineTwoComponent?.let { component ->
            provideComponentStyle(component, addressLineOTwoInputStyle, from)?.let { inputComponentStyle ->
                componentStylesValues[BillingFormFields.AddressLineTwo] = inputComponentStyle
            }
        }

        val cityComponent = from.paymentFormComponents.find {
            PaymentFormComponentField.City.name == it.paymentFormComponentField.name
        }
        val cityInputStyle = defaultComponentStylesValues[BillingFormFields.City]
        cityComponent?.let { component ->
            provideComponentStyle(component, cityInputStyle, from)?.let { inputComponentStyle ->
                componentStylesValues[BillingFormFields.City] = inputComponentStyle
            }
        }

        val stateComponent = from.paymentFormComponents.find {
            PaymentFormComponentField.State.name == it.paymentFormComponentField.name
        }
        val stateInputStyle = defaultComponentStylesValues[BillingFormFields.State]
        stateComponent?.let { component ->
            provideComponentStyle(component, stateInputStyle, from)?.let { inputComponentStyle ->
                componentStylesValues[BillingFormFields.State] = inputComponentStyle
            }
        }

        val postCodeComponent = from.paymentFormComponents.find {
            PaymentFormComponentField.PostCode.name == it.paymentFormComponentField.name
        }
        val postCodeInputStyle = defaultComponentStylesValues[BillingFormFields.PostCode]
        postCodeComponent?.let { component ->
            provideComponentStyle(component, postCodeInputStyle, from)?.let { inputComponentStyle ->
                componentStylesValues[BillingFormFields.PostCode] = inputComponentStyle
            }
        }

        val phoneComponent = from.paymentFormComponents.find {
            PaymentFormComponentField.Phone.name == it.paymentFormComponentField.name
        }
        val phoneInputStyle = defaultComponentStylesValues[BillingFormFields.Phone]
        phoneComponent?.let { component ->
            provideComponentStyle(component, phoneInputStyle, from)?.let { inputComponentStyle ->
                componentStylesValues[BillingFormFields.Phone] = inputComponentStyle
            }
        }

        if (!componentStylesValues.isEmpty()) {
            inputComponentsContainerStyle = inputComponentsContainerStyle.copy(
                inputComponentStyleValues = componentStylesValues
            )
        }

        return inputComponentsContainerStyle
    }

    private fun provideComponentStyle(
        component: PaymentFormComponent,
        inputComponentStyle: InputComponentStyle?,
        from: PaymentFormTheme,
    ) = if (!component.isFieldHidden)
        provideInputComponentStyle(inputComponentStyle, component, from) else null

    private fun provideInputComponentStyle(
        inputComponentStyle: InputComponentStyle?,
        component: PaymentFormComponent,
        from: PaymentFormTheme,
    ): InputComponentStyle? {
        return with(inputComponentStyle) {
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
