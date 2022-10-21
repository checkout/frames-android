package com.checkout.frames.screen.billingaddress.usecase

import com.checkout.base.mapper.Mapper
import com.checkout.base.usecase.UseCase
import com.checkout.frames.component.billingaddressfields.BillingAddressInputComponentsContainerState
import com.checkout.frames.component.billingaddressfields.BillingAddressInputComponentState
import com.checkout.frames.screen.manager.PaymentStateManager
import com.checkout.frames.style.component.billingformdetails.BillingAddressInputComponentStyle

internal class BillingAddressInputComponentStateUseCase(
    private val billingAddressInputComponentStateMapper: Mapper<BillingAddressInputComponentStyle,
            BillingAddressInputComponentState>,
    private val paymentStateManager: PaymentStateManager
) : UseCase<List<BillingAddressInputComponentStyle>, BillingAddressInputComponentsContainerState> {

    override fun execute(data: List<BillingAddressInputComponentStyle>): BillingAddressInputComponentsContainerState {
        val defaultBillingAddressInputComponentStateList: MutableList<BillingAddressInputComponentState> =
            mutableListOf()

        // Fill default  InputComponentState list
        data.forEach { billingFormDynamicFieldComponentStyle ->

            defaultBillingAddressInputComponentStateList.add(
                billingAddressInputComponentStateMapper.map(
                    billingFormDynamicFieldComponentStyle
                )
            )
        }

        return BillingAddressInputComponentsContainerState(
            addMandatoryBillingAddressInputComponentState(
                provideInputComponentStateList(defaultBillingAddressInputComponentStateList),
                defaultBillingAddressInputComponentStateList
            )
        )
    }

    // Fill all view state list from the paymentStateManager which contains all enums which provided by the merchant
    private fun provideInputComponentStateList(
        defaultBillingAddressInputComponentStateList: MutableList<BillingAddressInputComponentState>
    ): MutableList<BillingAddressInputComponentState> {

        val billingAddressInputComponentStateList: MutableList<BillingAddressInputComponentState> = mutableListOf()

        paymentStateManager.billingAddressFields.forEach { addressField ->
            val billingAddressInputComponentState = defaultBillingAddressInputComponentStateList.find {
                it.addressFieldName == addressField.addressFieldName
            }?.apply {
                isOptional = addressField.isOptional
            }
            billingAddressInputComponentState?.let { billingAddressInputComponentStateList.add(it) }
        }

        return billingAddressInputComponentStateList
    }

    private fun addMandatoryBillingAddressInputComponentState(
        processedList: MutableList<BillingAddressInputComponentState>,
        defaultBillingAddressInputComponentStateList: MutableList<BillingAddressInputComponentState>
    ): MutableList<BillingAddressInputComponentState> {

        paymentStateManager.mandatoryBillingAddressFields.forEach { mandatoryBillingAddressField ->
            if (processedList.none { it.addressFieldName == mandatoryBillingAddressField.addressFieldName }) {
                val billingAddressInputComponentState =
                    defaultBillingAddressInputComponentStateList.find {
                        it.addressFieldName == mandatoryBillingAddressField.addressFieldName
                    }

                val mandatoryInputComponentState = billingAddressInputComponentState?.inputComponentState?.let {
                    BillingAddressInputComponentState(
                        mandatoryBillingAddressField.addressFieldName,
                        mandatoryBillingAddressField.isOptional,
                        it,
                    )
                }

                mandatoryInputComponentState?.let { processedList.add(it) }
            }
        }
        return processedList
    }
}
