package com.checkout.frames.style.screen

import com.checkout.frames.style.component.CountryComponentStyle
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.billingformdetails.BillingAddressInputComponentsContainerStyle
import com.checkout.frames.style.component.billingformdetails.BillingAddressHeaderComponentStyle
import com.checkout.frames.style.component.default.DefaultCountryComponentStyle

public data class BillingAddressDetailsStyle(
    var billingAddressHeaderComponentStyle: BillingAddressHeaderComponentStyle = BillingAddressHeaderComponentStyle(),
    var billingAddressInputComponentsContainerStyle: BillingAddressInputComponentsContainerStyle =
        BillingAddressInputComponentsContainerStyle(),
    public var countryComponentStyle: CountryComponentStyle = DefaultCountryComponentStyle.light(),
    public var containerStyle: ContainerStyle = ContainerStyle()
)
