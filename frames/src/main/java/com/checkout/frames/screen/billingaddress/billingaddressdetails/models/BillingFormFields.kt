package com.checkout.frames.screen.billingaddress.billingaddressdetails.models

import com.checkout.frames.screen.billingaddress.billingaddressdetails.GetAddressField

/**
 * Enum representing BillingFormFields for BillingFormScreen.
 *
 *     FullName - Not mandatory and can be optional field
 *     AddressLineOne - Not mandatory and can be optional field
 *     AddressLineTwo - Not mandatory and can be optional field
 *     City - Not mandatory and can be optional field
 *     State - Not mandatory and can be optional field
 *     PostCode - Not mandatory and can be optional field
 *     Country - Mandatory and can not be optional field
 *     Phone - Mandatory and can not be optional field
 */
public enum class BillingFormFields : GetAddressField {
    FullName {
        override fun getAddressField(isOptional: Boolean, addressFieldName: String): AddressField =
            AddressField(isOptional, addressFieldName)
    },
    AddressLineOne {
        override fun getAddressField(isOptional: Boolean, addressFieldName: String): AddressField =
            AddressField(isOptional, addressFieldName)
    },
    AddressLineTwo {
        override fun getAddressField(isOptional: Boolean, addressFieldName: String): AddressField =
            AddressField(isOptional, addressFieldName)
    },
    City {
        override fun getAddressField(isOptional: Boolean, addressFieldName: String): AddressField =
            AddressField(isOptional, addressFieldName)
    },
    State {
        override fun getAddressField(isOptional: Boolean, addressFieldName: String): AddressField =
            AddressField(isOptional, addressFieldName)
    },
    PostCode {
        override fun getAddressField(isOptional: Boolean, addressFieldName: String): AddressField =
            AddressField(isOptional, addressFieldName)
    },
    Country {
        override fun getAddressField(isOptional: Boolean, addressFieldName: String): AddressField =
            AddressField(isOptional, addressFieldName)
    },
    PhoneNumber {
        override fun getAddressField(isOptional: Boolean, addressFieldName: String): AddressField =
            AddressField(isOptional, addressFieldName)
    };

    public companion object {
        public fun fetchAllDefaultBillingFormFields(): List<AddressField> = mutableListOf<AddressField>().apply {
            addAll(
                listOf(
                    FullName.getAddressField(false, FullName.name),
                    AddressLineOne.getAddressField(false, AddressLineOne.name),
                    AddressLineTwo.getAddressField(true, AddressLineTwo.name),
                    City.getAddressField(false, City.name),
                    State.getAddressField(true, State.name),
                    PostCode.getAddressField(false, PostCode.name),
                    Country.getAddressField(false, Country.name),
                    PhoneNumber.getAddressField(false, PhoneNumber.name)
                )
            )
        }

        public fun fetchAllMandatoryBillingFormFields(): List<AddressField> = mutableListOf<AddressField>().apply {
            addAll(
                listOf(
                    Country.getAddressField(false, Country.name),
                    PhoneNumber.getAddressField(false, PhoneNumber.name)
                )
            )
        }
    }
}
