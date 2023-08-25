package com.checkout.frames.screen.billingaddress.usecase

import com.checkout.base.mapper.Mapper
import com.checkout.base.usecase.UseCase
import com.checkout.frames.style.component.billingformdetails.BillingAddressInputComponentStyle
import com.checkout.frames.style.screen.BillingAddressDetailsStyle
import com.checkout.frames.style.view.BillingAddressInputComponentViewStyle
import com.checkout.frames.style.view.billingformdetails.BillingAddressInputComponentsViewContainerStyle

internal class BillingAddressInputComponentStyleUseCase(
    private val billingAddressInputComponentStyleMapper: Mapper<BillingAddressInputComponentStyle,
            BillingAddressInputComponentViewStyle>
) : UseCase<BillingAddressDetailsStyle, BillingAddressInputComponentsViewContainerStyle> {

    override fun execute(data: BillingAddressDetailsStyle): BillingAddressInputComponentsViewContainerStyle {
        val inputComponentViewStyleList = mutableListOf<BillingAddressInputComponentViewStyle>()

        data.inputComponentsContainerStyle.inputComponentStyleValues.forEach { inputComponentStyleValue ->

            inputComponentViewStyleList.add(
                billingAddressInputComponentStyleMapper.map(
                    BillingAddressInputComponentStyle(inputComponentStyleValue.key.name, inputComponentStyleValue.value)
                )
            )
        }

        return BillingAddressInputComponentsViewContainerStyle(inputComponentViewStyleList)
    }
}
