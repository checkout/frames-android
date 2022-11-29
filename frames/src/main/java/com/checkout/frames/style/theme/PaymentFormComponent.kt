package com.checkout.frames.style.theme

public data class PaymentFormComponent(
    val paymentFormComponentField: PaymentFormComponentField,
    val isFieldOptional: Boolean,
    val isFieldHidden: Boolean,
    val titleText: String,
    val titleTextId: Int? = null,
    val subTitleText: String,
    val subTitleTextId: Int? = null,
    val infoText: String,
    val infoTextId: Int? = null,
    val placeholderResourceText: String,
    val placeholderResourceTextId: Int? = null,
    val backIconImageResourceID: Int? = null,
    val leadingIconImageResourceID: Int? = null,
    val trailingIconImageResourceID: Int? = null
)
