package com.checkout.frames.component.billingaddressfields

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.autofill.AutofillType
import com.checkout.frames.component.base.InputComponent
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingFormFields
import com.checkout.frames.style.view.BillingAddressInputComponentViewStyle

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun BillingAddressDynamicInputComponent(
    position: Int,
    inputComponentViewStyle: BillingAddressInputComponentViewStyle,
    inputComponentState: BillingAddressInputComponentState,
    onFocusChanged: (Int, Boolean) -> Unit,
    onValueChange: (Int, String) -> Unit
) {
    val autofillType = when(inputComponentViewStyle.addressFieldName) {
        BillingFormFields.AddressLineOne.name -> {
            AutofillType.AddressStreet
        }
        BillingFormFields.AddressLineTwo.name -> {
            AutofillType.AddressAuxiliaryDetails
        }
        BillingFormFields.City.name -> {
            AutofillType.AddressLocality
        }
        BillingFormFields.State.name -> {
            AutofillType.AddressRegion
        }
        BillingFormFields.PostCode.name -> {
            AutofillType.PostalCode
        }
        else -> null
    }
    InputComponent(
        style = inputComponentViewStyle.inputComponentViewStyle,
        state = inputComponentState.inputComponentState,
        autofillType = autofillType,
        onFocusChanged = { onFocusChanged(position, it) },
        onValueChange = { onValueChange(position, it) }
    )
}
