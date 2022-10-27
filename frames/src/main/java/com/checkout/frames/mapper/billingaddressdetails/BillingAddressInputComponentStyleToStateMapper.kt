package com.checkout.frames.mapper.billingaddressdetails

import com.checkout.base.mapper.Mapper
import com.checkout.frames.component.base.InputComponentState
import com.checkout.frames.component.billingaddressfields.BillingAddressInputComponentState
import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.style.component.billingformdetails.BillingAddressInputComponentStyle

internal class BillingAddressInputComponentStyleToStateMapper(
    private val inputComponentStateMapper: Mapper<InputComponentStyle, InputComponentState>,
) : Mapper<BillingAddressInputComponentStyle, BillingAddressInputComponentState> {

    override fun map(from: BillingAddressInputComponentStyle) = BillingAddressInputComponentState(
        mappedInputComponentState = provideInputComponentState(from)
    )

    private fun provideInputComponentState(from: BillingAddressInputComponentStyle):
            Map.Entry<String, InputComponentState> {

        val inputComponentStateLinkedHashMap: LinkedHashMap<String, InputComponentState> = linkedMapOf()
        inputComponentStateLinkedHashMap[from.inputComponentStyleMappedEntry.key] =
            inputComponentStateMapper.map(from.inputComponentStyleMappedEntry.value)

        return inputComponentStateLinkedHashMap.entries.first()
    }
}
