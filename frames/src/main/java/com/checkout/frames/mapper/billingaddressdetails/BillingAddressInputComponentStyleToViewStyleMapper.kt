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
        inputComponentViewStyleMappedEntry = provideInputComponentViewStyle(from)
    )

    private fun provideInputComponentViewStyle(from: BillingAddressInputComponentStyle):
            Map.Entry<String, InputComponentViewStyle> {
        val inputComponentViewStyleLinkedHashMap: LinkedHashMap<String, InputComponentViewStyle> = linkedMapOf()
        inputComponentViewStyleLinkedHashMap[from.inputComponentStyleMappedEntry.key] =
            inputComponentStyleMapper.map(from.inputComponentStyleMappedEntry.value)

        return inputComponentViewStyleLinkedHashMap.entries.first()
    }
}
