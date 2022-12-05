package com.checkout.frames.style.theme

public data class PaymentFormComponent(
    val paymentFormComponentField: PaymentFormComponentField,
    val isFieldOptional: Boolean = false,
    val isFieldHidden: Boolean = false,
    val titleText: String? = null,
    val titleTextId: Int? = null,
    val subTitleText: String? = null,
    val subTitleTextId: Int? = null,
    val infoText: String? = null,
    val infoTextId: Int? = null,
    val placeholderResourceText: String? = null,
    val placeholderResourceTextId: Int? = null,
    val backIconImageResourceID: Int? = null,
    val leadingIconImageResourceID: Int? = null,
    val trailingIconImageResourceID: Int? = null
)
