package com.checkout.frames.screen.billingaddress.usecase

import com.checkout.base.mapper.Mapper
import com.checkout.base.usecase.UseCase
import com.checkout.frames.screen.manager.PaymentStateManager
import com.checkout.frames.style.component.billingformdetails.BillingAddressInputComponentStyle
import com.checkout.frames.style.view.BillingAddressInputComponentViewStyle

internal class BillingAddressInputComponentStyleUseCase(
    private val billingAddressInputComponentStyleMapper: Mapper<BillingAddressInputComponentStyle,
            BillingAddressInputComponentViewStyle>,
    private val paymentStateManager: PaymentStateManager
) : UseCase<List<BillingAddressInputComponentStyle>, List<BillingAddressInputComponentViewStyle>> {

    override fun execute(data: List<BillingAddressInputComponentStyle>): List<BillingAddressInputComponentViewStyle> {
        val defaultBillingAddressInputComponentViewStyleList = mutableListOf<BillingAddressInputComponentViewStyle>()

        // Fill default view styling list
        data.forEach { billingFormDynamicFieldComponentStyle ->
            defaultBillingAddressInputComponentViewStyleList.add(
                billingAddressInputComponentStyleMapper.map(billingFormDynamicFieldComponentStyle)
            )
        }

        return addMandatoryBillingAddressInputComponentViewStyle(
            provideBillingAddressInputComponentViewStyleList(defaultBillingAddressInputComponentViewStyleList),
            defaultBillingAddressInputComponentViewStyleList
        )
    }

    // Fill all view styling list from the paymentStateManager which contains all enums which provided by the merchant
    private fun provideBillingAddressInputComponentViewStyleList(
        defaultBillingAddressInputComponentViewStyleList: MutableList<BillingAddressInputComponentViewStyle>
    ): MutableList<BillingAddressInputComponentViewStyle> {

        val billingAddressInputComponentViewStyleList: MutableList<BillingAddressInputComponentViewStyle> =
            mutableListOf()

        paymentStateManager.billingAddressFields.forEach { addressField ->
            val billingAddressInputComponentViewStyle: BillingAddressInputComponentViewStyle? =
                defaultBillingAddressInputComponentViewStyleList.find {
                    it.addressFieldName == addressField.addressFieldName
                }?.apply {
                    isOptional = addressField.isOptional
                }

            billingAddressInputComponentViewStyle?.let { billingAddressInputComponentViewStyleList.add(it) }
        }

        return billingAddressInputComponentViewStyleList
    }

    private fun addMandatoryBillingAddressInputComponentViewStyle(
        billingAddressInputComponentViewStyleList: MutableList<BillingAddressInputComponentViewStyle>,
        defaultBillingAddressInputComponentViewStyleList: MutableList<BillingAddressInputComponentViewStyle>
    ): MutableList<BillingAddressInputComponentViewStyle> {
        paymentStateManager.mandatoryBillingAddressFields.forEach { mandatoryBillingAddressField ->
            if (
                billingAddressInputComponentViewStyleList.none {
                    it.addressFieldName == mandatoryBillingAddressField.addressFieldName
            }
            ) {
                val billingAddressInputComponentViewStyle =
                    defaultBillingAddressInputComponentViewStyleList.find {
                        it.addressFieldName == mandatoryBillingAddressField.addressFieldName
                    }

                val mandatoryBillingAddressInputComponentViewStyle =
                    billingAddressInputComponentViewStyle?.inputComponentViewStyle?.let {
                        BillingAddressInputComponentViewStyle(
                            mandatoryBillingAddressField.addressFieldName,
                            mandatoryBillingAddressField.isOptional,
                            it,
                        )
                    }
                mandatoryBillingAddressInputComponentViewStyle?.let {
                    billingAddressInputComponentViewStyleList.add(it)
                }
            }
        }

        return billingAddressInputComponentViewStyleList
    }
}
