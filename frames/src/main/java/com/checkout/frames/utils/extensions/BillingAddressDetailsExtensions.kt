package com.checkout.frames.utils.extensions

import androidx.annotation.StringRes
import com.checkout.base.model.Country
import com.checkout.frames.R
import com.checkout.frames.component.billingaddressfields.BillingAddressInputComponentState
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingAddress
import com.checkout.frames.screen.billingaddress.billingaddressdetails.models.BillingFormFields
import com.checkout.tokenization.model.Address
import com.checkout.tokenization.model.Phone

internal fun List<BillingAddressInputComponentState>.provideBillingAddressDetails(
    country: Country? = null
): BillingAddress {
    val phoneInputComponentState = this.find {
        it.addressFieldName == BillingFormFields.Phone.name
    }

    val addressLineOneInputComponentState = this.find {
        it.addressFieldName == BillingFormFields.AddressLineOne.name
    }

    val cardHolderNameComponentState = this.find {
        it.addressFieldName == BillingFormFields.CardHolderName.name
    }

    val addressLineTwoInputComponentState = this.find {
        it.addressFieldName == BillingFormFields.AddressLineTwo.name
    }

    val stateInputComponentState = this.find {
        it.addressFieldName == BillingFormFields.State.name
    }

    val cityInputComponentState = this.find {
        it.addressFieldName == BillingFormFields.City.name
    }

    val zipInputComponentState = this.find {
        it.addressFieldName == BillingFormFields.PostCode.name
    }

    return BillingAddress(
        name = cardHolderNameComponentState?.addressFieldText?.value?.trimEnd() ?: "",
        address = Address(
            country = country,
            addressLine1 = addressLineOneInputComponentState?.addressFieldText?.value?.trimEnd() ?: "",
            addressLine2 = addressLineTwoInputComponentState?.addressFieldText?.value?.trimEnd() ?: "",
            state = stateInputComponentState?.addressFieldText?.value?.trimEnd() ?: "",
            city = cityInputComponentState?.addressFieldText?.value?.trimEnd() ?: "",
            zip = zipInputComponentState?.addressFieldText?.value?.trimEnd() ?: ""
        ),
        phone = phoneInputComponentState?.let { componentState ->
            componentState.addressFieldText.value.takeIf { it.isNotEmpty() }
                ?.let { Phone(componentState.addressFieldText.value, country) }
        }
    )
}

@StringRes
internal fun BillingAddressInputComponentState.getErrorMessage(): Int {
    return when (this.addressFieldName) {
        BillingFormFields.CardHolderName.name -> {
            R.string.cko_cardholder_name_error
        }
        BillingFormFields.AddressLineOne.name -> {
            R.string.cko_billing_form_input_field_address_line_one_error
        }
        BillingFormFields.AddressLineTwo.name -> {
            R.string.cko_billing_form_input_field_address_line_two_error
        }
        BillingFormFields.City.name -> {
            R.string.cko_billing_form_input_field_city_error
        }
        BillingFormFields.State.name -> {
            R.string.cko_billing_form_input_field_state_error
        }
        BillingFormFields.PostCode.name -> {
            R.string.cko_billing_form_input_field_post_code_error
        }
        BillingFormFields.Phone.name -> {
            R.string.cko_billing_form_input_field_phone_number_error
        }
        else -> {
            0
        }
    }
}

internal fun BillingAddress.provideAddressFieldText(
    addressFieldName: String
): String {
    return when (addressFieldName) {
        BillingFormFields.CardHolderName.name -> {
            this.name ?: ""
        }
        BillingFormFields.AddressLineOne.name -> {
            this.address?.addressLine1 ?: ""
        }
        BillingFormFields.AddressLineTwo.name -> {
            this.address?.addressLine2 ?: ""
        }
        BillingFormFields.City.name -> {
            this.address?.city ?: ""
        }
        BillingFormFields.State.name -> {
            this.address?.state ?: ""
        }
        BillingFormFields.PostCode.name -> {
            this.address?.zip ?: ""
        }
        BillingFormFields.Phone.name -> {
            this.phone?.number ?: ""
        }
        else -> {
            ""
        }
    }
}
