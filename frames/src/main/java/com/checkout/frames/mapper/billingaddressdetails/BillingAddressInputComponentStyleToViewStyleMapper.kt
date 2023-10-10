package com.checkout.frames.mapper.billingaddressdetails

import com.checkout.base.mapper.Mapper
import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.style.component.billingformdetails.BillingAddressInputComponentStyle
import com.checkout.frames.style.view.BillingAddressInputComponentViewStyle
import com.checkout.frames.style.view.InputComponentViewStyle

internal class BillingAddressInputComponentStyleToViewStyleMapper(
    private val inputComponentStyleMapper: Mapper<InputComponentStyle, InputComponentViewStyle>,
) : Mapper<BillingAddressInputComponentStyle, BillingAddressInputComponentViewStyle> {

    override fun map(from: BillingAddressInputComponentStyle) = BillingAddressInputComponentViewStyle(
        addressFieldName = from.addressFieldName,
        inputComponentViewStyle = inputComponentStyleMapper.map(from.inputComponentStyle),
    )
}
