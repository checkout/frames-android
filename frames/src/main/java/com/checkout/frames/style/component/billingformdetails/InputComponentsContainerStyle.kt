package com.checkout.frames.style.component.billingformdetails

import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingFormFields
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.InputComponentStyle

public data class InputComponentsContainerStyle @JvmOverloads constructor(
    val inputComponentStyleValues: LinkedHashMap<BillingFormFields, InputComponentStyle> = linkedMapOf(),
    var containerStyle: ContainerStyle = ContainerStyle()
)
