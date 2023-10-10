package com.checkout.frames.mapper.billingaddressdetails

import com.checkout.base.mapper.Mapper
import com.checkout.frames.component.base.InputComponentState
import com.checkout.frames.component.billingaddressfields.BillingAddressInputComponentState
import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.style.component.billingformdetails.BillingAddressInputComponentStyle
import kotlinx.coroutines.flow.MutableStateFlow

internal class BillingAddressInputComponentStyleToStateMapper(
    private val inputComponentStateMapper: Mapper<InputComponentStyle, InputComponentState>,
) : Mapper<BillingAddressInputComponentStyle, BillingAddressInputComponentState> {

    override fun map(from: BillingAddressInputComponentStyle) = BillingAddressInputComponentState(
        addressFieldName = from.addressFieldName,
        isAddressFieldValid = MutableStateFlow(from.inputComponentStyle.isInputFieldOptional),
        inputComponentState = inputComponentStateMapper.map(from.inputComponentStyle),
    )
}
