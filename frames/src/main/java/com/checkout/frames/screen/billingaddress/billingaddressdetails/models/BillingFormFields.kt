package com.checkout.frames.screen.billingaddress.billingaddressdetails.models

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
public enum class BillingFormFields(internal var isFieldOptional: Boolean = false) {
    FullName,
    AddressLineOne,
    AddressLineTwo,
    City,
    State,
    PostCode,
    Country,
    Phone;

    public companion object {

        public fun fetchAllDefaultBillingFormFields(): List<BillingFormFields> = mutableListOf<BillingFormFields>()
            .apply {
                addAll(
                    listOf(
                        FullName.withOptional(false),
                        AddressLineOne.withOptional(false),
                        AddressLineTwo.withOptional(true),
                        City.withOptional(false),
                        State.withOptional(true),
                        PostCode.withOptional(false),
                        Country.withOptional(false),
                        Phone.withOptional(false)
                    )
                )
            }

        public fun fetchAllMandatoryBillingFormFields(): List<BillingFormFields> = mutableListOf<BillingFormFields>()
            .apply {
                addAll(
                    listOf(
                        Country.withOptional(false),
                        Phone.withOptional(false)
                    )
                )
            }
    }
}

public fun BillingFormFields.withOptional(isOptional: Boolean): BillingFormFields =
    this.apply { isFieldOptional = isOptional }
