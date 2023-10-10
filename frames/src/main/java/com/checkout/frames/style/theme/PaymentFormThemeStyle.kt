package com.checkout.frames.style.theme

import com.checkout.frames.style.component.base.ButtonStyle
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.InputFieldIndicatorStyle
import com.checkout.frames.style.component.base.TextStyle

internal data class PaymentFormThemeStyle(
    val titleTextStyle: TextStyle? = null,
    val subTitleTextStyle: TextStyle? = null,
    val infoTextStyle: TextStyle? = null,
    val inputFieldTextStyle: TextStyle = TextStyle(),
    val errorMessageTextStyle: TextStyle? = null,
    val addressTextStyle: TextStyle? = null,
    val inputFieldIndicatorStyle: InputFieldIndicatorStyle,
    val addAddressButtonStyle: ButtonStyle = ButtonStyle(),
    val editAddressButtonStyle: ButtonStyle = ButtonStyle(),
    val paymentDetailsButtonStyle: ButtonStyle = ButtonStyle(),
    val inputFieldContainerStyle: ContainerStyle = ContainerStyle(),
    val containerStyle: ContainerStyle = ContainerStyle(),
)
