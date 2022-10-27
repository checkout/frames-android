package com.checkout.frames.style.screen

import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingFormFields
import com.checkout.frames.style.component.CountryComponentStyle
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.billingformdetails.InputComponentsContainerStyle
import com.checkout.frames.style.component.billingformdetails.HeaderComponentStyle
import com.checkout.frames.style.component.default.DefaultCountryComponentStyle

public data class BillingAddressDetailsStyle(
    var headerComponentStyle: HeaderComponentStyle = HeaderComponentStyle(),
    var inputComponentsContainerStyle: InputComponentsContainerStyle =
        InputComponentsContainerStyle(),
    val billingFormFieldList: List<BillingFormFields> = emptyList(),
    var countryComponentStyle: CountryComponentStyle = DefaultCountryComponentStyle.light(),
    var containerStyle: ContainerStyle = ContainerStyle()
)
