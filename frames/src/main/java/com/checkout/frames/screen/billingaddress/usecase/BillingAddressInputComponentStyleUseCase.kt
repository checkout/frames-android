package com.checkout.frames.screen.billingaddress.usecase

import com.checkout.base.mapper.Mapper
import com.checkout.base.usecase.UseCase
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingFormFields
import com.checkout.frames.style.component.billingformdetails.BillingAddressInputComponentStyle
import com.checkout.frames.style.screen.BillingAddressDetailsStyle
import com.checkout.frames.style.view.BillingAddressInputComponentViewStyle
import com.checkout.frames.style.view.billingformdetails.BillingAddressInputComponentsViewContainerStyle

internal class BillingAddressInputComponentStyleUseCase(
    private val billingAddressInputComponentStyleMapper: Mapper<BillingAddressInputComponentStyle,
            BillingAddressInputComponentViewStyle>
) : UseCase<BillingAddressDetailsStyle, BillingAddressInputComponentsViewContainerStyle> {

    override fun execute(data: BillingAddressDetailsStyle): BillingAddressInputComponentsViewContainerStyle {
        val defaultBillingAddressInputComponentViewStyleList = mutableListOf<BillingAddressInputComponentViewStyle>()

        // Fill default view styling list
        data.inputComponentsContainerStyle.inputComponentStyleList.forEach { billingFormDynamicFieldComponentStyle ->

            defaultBillingAddressInputComponentViewStyleList.add(
                billingAddressInputComponentStyleMapper.map(
                    BillingAddressInputComponentStyle(billingFormDynamicFieldComponentStyle)
                )
            )
        }

        return BillingAddressInputComponentsViewContainerStyle(
            addMandatoryInputComponentViewStyle(
                provideInputComponentViewStyleList(
                    data.billingFormFieldList,
                    defaultBillingAddressInputComponentViewStyleList
                ),
                defaultBillingAddressInputComponentViewStyleList
            )
        )
    }

    private fun provideInputComponentViewStyleList(
        billingAddressFields: List<BillingFormFields>,
        defaultBillingAddressInputComponentViewStyleList: MutableList<BillingAddressInputComponentViewStyle>
    ): MutableList<BillingAddressInputComponentViewStyle> {

        val inputComponentViewStyleList: MutableList<BillingAddressInputComponentViewStyle> =
            mutableListOf()

        billingAddressFields.ifEmpty {
            BillingFormFields.fetchAllDefaultBillingFormFields()
        }.forEach { addressField ->
            val billingAddressInputComponentViewStyle: BillingAddressInputComponentViewStyle? =
                defaultBillingAddressInputComponentViewStyleList.find {
                    it.inputComponentViewStyleMappedEntry.key == addressField.name
                }

            // Check duplicate entry if merchant provided duplicate styles for fields
            billingAddressInputComponentViewStyle?.let {
                if (!inputComponentViewStyleList.contains(it)) {
                    inputComponentViewStyleList.add(it)
                }
            }
        }

        return inputComponentViewStyleList
    }

    private fun addMandatoryInputComponentViewStyle(
        inputComponentViewStyleList: MutableList<BillingAddressInputComponentViewStyle>,
        defaultInputComponentViewStyleList: MutableList<BillingAddressInputComponentViewStyle>
    ): MutableList<BillingAddressInputComponentViewStyle> {
        BillingFormFields.fetchAllMandatoryBillingFormFields().forEach { mandatoryBillingAddressField ->
            if (
                inputComponentViewStyleList.none {
                    it.inputComponentViewStyleMappedEntry.key == mandatoryBillingAddressField.name
                }
            ) {
                val billingAddressInputComponentViewStyle =
                    defaultInputComponentViewStyleList.find {
                        it.inputComponentViewStyleMappedEntry.key == mandatoryBillingAddressField.name
                    }

                val mandatoryInputComponentViewStyle =
                    billingAddressInputComponentViewStyle?.inputComponentViewStyleMappedEntry?.let {
                        BillingAddressInputComponentViewStyle(it)
                    }
                mandatoryInputComponentViewStyle?.let {
                    inputComponentViewStyleList.add(it)
                }
            }
        }

        return inputComponentViewStyleList
    }
}
