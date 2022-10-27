package com.checkout.frames.screen.billingaddress.usecase

import com.checkout.base.mapper.Mapper
import com.checkout.base.usecase.UseCase
import com.checkout.frames.component.billingaddressfields.BillingAddressInputComponentsContainerState
import com.checkout.frames.component.billingaddressfields.BillingAddressInputComponentState
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingFormFields
import com.checkout.frames.style.component.billingformdetails.BillingAddressInputComponentStyle
import com.checkout.frames.style.screen.BillingAddressDetailsStyle

internal class BillingAddressInputComponentStateUseCase(
    private val billingAddressInputComponentStateMapper: Mapper<BillingAddressInputComponentStyle,
            BillingAddressInputComponentState>
) : UseCase<BillingAddressDetailsStyle, BillingAddressInputComponentsContainerState> {

    override fun execute(data: BillingAddressDetailsStyle): BillingAddressInputComponentsContainerState {
        val defaultBillingAddressInputComponentStateList: MutableList<BillingAddressInputComponentState> =
            mutableListOf()

        // Fill default  InputComponentState list
        data.inputComponentsContainerStyle.inputComponentStyleList.forEach { billingFormDynamicFieldComponentStyle ->

            defaultBillingAddressInputComponentStateList.add(
                billingAddressInputComponentStateMapper.map(
                    BillingAddressInputComponentStyle(billingFormDynamicFieldComponentStyle)
                )
            )
        }

        return BillingAddressInputComponentsContainerState(
            addMandatoryInputComponentsState(
                provideInputComponentStateList(
                    data.billingFormFieldList,
                    defaultBillingAddressInputComponentStateList
                ),
                defaultBillingAddressInputComponentStateList
            )
        )
    }

    // Fill all view state list from the paymentStateManager which contains all enums which provided by the merchant
    private fun provideInputComponentStateList(
        billingAddressFields: List<BillingFormFields>,
        defaultBillingAddressInputComponentStateList: MutableList<BillingAddressInputComponentState>
    ): MutableList<BillingAddressInputComponentState> {
        val inputComponentStateList: MutableList<BillingAddressInputComponentState> = mutableListOf()

        billingAddressFields.ifEmpty {
            BillingFormFields.fetchAllDefaultBillingFormFields()
        }.forEach { addressField ->
            val billingAddressInputComponentState = defaultBillingAddressInputComponentStateList.find {
                it.mappedInputComponentState.key == addressField.name
            }
            billingAddressInputComponentState?.let {
                if (!inputComponentStateList.contains(it)) {
                    inputComponentStateList.add(it)
                }
            }
        }

        return inputComponentStateList
    }

    private fun addMandatoryInputComponentsState(
        processedList: MutableList<BillingAddressInputComponentState>,
        defaultBillingAddressInputComponentStateList: MutableList<BillingAddressInputComponentState>
    ): MutableList<BillingAddressInputComponentState> {

        BillingFormFields.fetchAllMandatoryBillingFormFields().forEach { mandatoryBillingAddressField ->
            if (processedList.none { it.mappedInputComponentState.key == mandatoryBillingAddressField.name }) {

                val billingAddressInputComponentState =
                    defaultBillingAddressInputComponentStateList.find {
                        it.mappedInputComponentState.key == mandatoryBillingAddressField.name
                    }

                val mandatoryInputComponentState = billingAddressInputComponentState?.mappedInputComponentState?.let {
                    BillingAddressInputComponentState(
                        it
                    )
                }

                mandatoryInputComponentState?.let { processedList.add(it) }
            }
        }
        return processedList
    }
}
