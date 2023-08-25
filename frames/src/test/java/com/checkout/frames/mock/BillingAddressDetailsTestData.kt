package com.checkout.frames.mock

import com.checkout.frames.component.base.InputComponentState
import com.checkout.frames.component.billingaddressfields.BillingAddressInputComponentState
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingFormFields
import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.style.component.billingformdetails.InputComponentsContainerStyle
import com.checkout.frames.style.view.BillingAddressInputComponentViewStyle
import com.checkout.frames.style.view.InputComponentViewStyle
import com.checkout.frames.style.view.InputFieldViewStyle
import com.checkout.frames.style.view.TextLabelViewStyle
import kotlinx.coroutines.flow.MutableStateFlow

internal object BillingAddressDetailsTestData {
    fun fetchInputComponentStateList(): List<BillingAddressInputComponentState> {
        val inputComponentStateList: MutableList<BillingAddressInputComponentState> = mutableListOf()

        val cardHolderNameState = BillingAddressInputComponentState(
            addressFieldName = BillingFormFields.CardHolderName.name,
            isAddressFieldValid = MutableStateFlow(false),
            inputComponentState = InputComponentState()
        )

        val addressLineOneState = BillingAddressInputComponentState(
            addressFieldName = BillingFormFields.AddressLineOne.name,
            isAddressFieldValid = MutableStateFlow(false),
            inputComponentState = InputComponentState()
        )

        val addressLineTwoState = BillingAddressInputComponentState(
            addressFieldName = BillingFormFields.AddressLineTwo.name,
            isAddressFieldValid = MutableStateFlow(false),
            inputComponentState = InputComponentState()
        )

        val cityState = BillingAddressInputComponentState(
            addressFieldName = BillingFormFields.City.name,
            isAddressFieldValid = MutableStateFlow(false),
            inputComponentState = InputComponentState()
        )

        val countyState = BillingAddressInputComponentState(
            addressFieldName = BillingFormFields.State.name,
            isAddressFieldValid = MutableStateFlow(false),
            inputComponentState = InputComponentState()
        )

        val postCodeState = BillingAddressInputComponentState(
            addressFieldName = BillingFormFields.PostCode.name,
            isAddressFieldValid = MutableStateFlow(false),
            inputComponentState = InputComponentState()
        )

        val phoneState = BillingAddressInputComponentState(
            addressFieldName = BillingFormFields.Phone.name,
            isAddressFieldValid = MutableStateFlow(false),
            inputComponentState = InputComponentState()
        )

        val countryState = BillingAddressInputComponentState(
            addressFieldName = BillingFormFields.Country.name,
            isAddressFieldValid = MutableStateFlow(false),
            inputComponentState = InputComponentState()
        )

        inputComponentStateList.add(cardHolderNameState)
        inputComponentStateList.add(addressLineOneState)
        inputComponentStateList.add(addressLineTwoState)
        inputComponentStateList.add(cityState)
        inputComponentStateList.add(countyState)
        inputComponentStateList.add(postCodeState)
        inputComponentStateList.add(phoneState)
        inputComponentStateList.add(countryState)

        return inputComponentStateList
    }

    fun fetchInputComponentStyleList(): List<BillingAddressInputComponentViewStyle> {
        val inputComponentStyleList: MutableList<BillingAddressInputComponentViewStyle> = mutableListOf()

        val cardHolderNameViewStyle = BillingAddressInputComponentViewStyle(
            addressFieldName = BillingFormFields.CardHolderName.name,
            inputComponentViewStyle = InputComponentViewStyle(
                isInputFieldOptional = false,
                errorMessageStyle = TextLabelViewStyle(),
                infoStyle = TextLabelViewStyle(),
                inputFieldStyle = InputFieldViewStyle(),
                subtitleStyle = TextLabelViewStyle(),
                titleStyle = TextLabelViewStyle()
            )
        )

        val addressLineOneViewStyle = BillingAddressInputComponentViewStyle(
            addressFieldName = BillingFormFields.AddressLineOne.name,
            inputComponentViewStyle = InputComponentViewStyle(
                isInputFieldOptional = false,
                errorMessageStyle = TextLabelViewStyle(),
                infoStyle = TextLabelViewStyle(),
                inputFieldStyle = InputFieldViewStyle(),
                subtitleStyle = TextLabelViewStyle(),
                titleStyle = TextLabelViewStyle()
            )
        )

        val addressLineTwoViewStyle = BillingAddressInputComponentViewStyle(
            addressFieldName = BillingFormFields.AddressLineTwo.name,
            inputComponentViewStyle = InputComponentViewStyle(
                isInputFieldOptional = false,
                errorMessageStyle = TextLabelViewStyle(),
                infoStyle = TextLabelViewStyle(),
                inputFieldStyle = InputFieldViewStyle(),
                subtitleStyle = TextLabelViewStyle(),
                titleStyle = TextLabelViewStyle()
            )
        )

        val phoneViewStyle = BillingAddressInputComponentViewStyle(
            addressFieldName = BillingFormFields.Phone.name,
            inputComponentViewStyle = InputComponentViewStyle(
                isInputFieldOptional = false,
                errorMessageStyle = TextLabelViewStyle(),
                infoStyle = TextLabelViewStyle(),
                inputFieldStyle = InputFieldViewStyle(),
                subtitleStyle = TextLabelViewStyle(),
                titleStyle = TextLabelViewStyle()
            )
        )

        inputComponentStyleList.add(cardHolderNameViewStyle)
        inputComponentStyleList.add(addressLineOneViewStyle)
        inputComponentStyleList.add(addressLineTwoViewStyle)
        inputComponentStyleList.add(phoneViewStyle)

        return inputComponentStyleList
    }

    @Suppress("LongMethod")
    fun fetchInputComponentStyleValues(): LinkedHashMap<BillingFormFields, InputComponentStyle> {
        val inputComponentsStyles: LinkedHashMap<BillingFormFields, InputComponentStyle> = linkedMapOf()

        inputComponentsStyles[BillingFormFields.CardHolderName] = InputComponentStyle()
        inputComponentsStyles[BillingFormFields.AddressLineOne] = InputComponentStyle()
        inputComponentsStyles[BillingFormFields.AddressLineTwo] = InputComponentStyle()
        inputComponentsStyles[BillingFormFields.City] = InputComponentStyle()
        inputComponentsStyles[BillingFormFields.State] = InputComponentStyle()
        inputComponentsStyles[BillingFormFields.PostCode] = InputComponentStyle()
        inputComponentsStyles[BillingFormFields.Phone] = InputComponentStyle()
        inputComponentsStyles[BillingFormFields.Country] = InputComponentStyle()

        return inputComponentsStyles
    }

    fun inputComponentsContainerStyle(): InputComponentsContainerStyle = InputComponentsContainerStyle(
        fetchInputComponentStyleValues()
    )
}
