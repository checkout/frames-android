package com.checkout.frames.screen.billingaddress.usecase

import com.checkout.base.mapper.Mapper
import com.checkout.base.usecase.UseCase
import com.checkout.frames.component.billingaddressfields.BillingAddressInputComponentsContainerState
import com.checkout.frames.component.billingaddressfields.BillingAddressInputComponentState
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingFormFields
import com.checkout.frames.style.component.billingformdetails.BillingAddressInputComponentStyle
import com.checkout.frames.style.screen.BillingAddressDetailsStyle
import com.checkout.frames.style.screen.default.DefaultBillingAddressDetailsStyle

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

        return BillingAddressInputComponentsContainerState(
            addMandatoryInputComponentsStateList(
                provideDefaultInputComponentViewStateList(),
                inputComponentStateList
            )
        )
    }

    private fun addMandatoryInputComponentsStateList(
        defaultInputComponentStateList: MutableList<BillingAddressInputComponentState>,
        inputComponentStateList: MutableList<BillingAddressInputComponentState>
    ): MutableList<BillingAddressInputComponentState> {

        BillingFormFields.fetchAllMandatoryBillingFormFields().forEach { mandatoryBillingFormField ->
            if (inputComponentStateList.none { it.addressFieldName == mandatoryBillingFormField.name }) {

                val inputComponentState =
                    defaultInputComponentStateList.find {
                        it.addressFieldName == mandatoryBillingFormField.name
                    }

                inputComponentState?.let {
                    inputComponentStateList.add(inputComponentState)
                }
            }
        }
        return inputComponentStateList
    }

    private fun provideDefaultInputComponentViewStateList(): MutableList<BillingAddressInputComponentState> {
        val inputComponentViewStateList = mutableListOf<BillingAddressInputComponentState>()

        DefaultBillingAddressDetailsStyle.fetchInputComponentStyleValues().forEach {
                billingFormDynamicFieldComponentStyle ->

            inputComponentViewStateList.add(
                billingAddressInputComponentStateMapper.map(
                    BillingAddressInputComponentStyle(
                        billingFormDynamicFieldComponentStyle.key.name,
                        billingFormDynamicFieldComponentStyle.value
                    )
                )
            )
        }
        return inputComponentViewStateList
    }
}
