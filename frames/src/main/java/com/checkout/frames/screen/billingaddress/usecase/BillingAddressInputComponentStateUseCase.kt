package com.checkout.frames.screen.billingaddress.usecase

import com.checkout.base.mapper.Mapper
import com.checkout.base.usecase.UseCase
import com.checkout.frames.component.billingaddressfields.BillingAddressInputComponentsContainerState
import com.checkout.frames.component.billingaddressfields.BillingAddressInputComponentState
import com.checkout.frames.style.component.billingformdetails.BillingAddressInputComponentStyle
import com.checkout.frames.style.screen.BillingAddressDetailsStyle

internal class BillingAddressInputComponentStateUseCase(
    private val billingAddressInputComponentStateMapper: Mapper<BillingAddressInputComponentStyle,
            BillingAddressInputComponentState>
) : UseCase<BillingAddressDetailsStyle, BillingAddressInputComponentsContainerState> {

    override fun execute(data: BillingAddressDetailsStyle): BillingAddressInputComponentsContainerState {
        val inputComponentStateList: MutableList<BillingAddressInputComponentState> = mutableListOf()

        data.inputComponentsContainerStyle.inputComponentStyleValues.forEach { inputComponentStyleValue ->

                inputComponentStateList.add(
                    billingAddressInputComponentStateMapper.map(
                        BillingAddressInputComponentStyle(
                            inputComponentStyleValue.key.name,
                            inputComponentStyleValue.value
                        )
                    )
                )
        }

        return BillingAddressInputComponentsContainerState(inputComponentStateList)
    }
}
