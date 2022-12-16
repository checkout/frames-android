package com.checkout.frames.style.screen

import com.checkout.frames.style.component.CountryComponentStyle
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.billingformdetails.InputComponentsContainerStyle
import com.checkout.frames.style.component.billingformdetails.HeaderComponentStyle
import com.checkout.frames.style.component.default.DefaultCountryComponentStyle
import com.checkout.frames.style.screen.default.DefaultBillingAddressDetailsStyle

public data class BillingAddressDetailsStyle(
    var headerComponentStyle: HeaderComponentStyle = DefaultBillingAddressDetailsStyle.headerComponentStyle(),
    var inputComponentsContainerStyle: InputComponentsContainerStyle =
        DefaultBillingAddressDetailsStyle.inputComponentsContainerStyle(),
    var countryComponentStyle: CountryComponentStyle = DefaultCountryComponentStyle.light(),
    var containerStyle: ContainerStyle = ContainerStyle(color = 0xFFFFFFFF)
)
