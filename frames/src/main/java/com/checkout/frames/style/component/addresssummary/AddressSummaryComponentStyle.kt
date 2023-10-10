package com.checkout.frames.style.component.addresssummary

import com.checkout.frames.style.component.base.ButtonStyle
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.TextLabelStyle

public data class AddressSummaryComponentStyle @JvmOverloads constructor(
    val titleStyle: TextLabelStyle? = null,
    val subTitleStyle: TextLabelStyle? = null,
    val addAddressButtonStyle: ButtonStyle = ButtonStyle(),
    val summarySectionStyle: AddressSummarySectionStyle = AddressSummarySectionStyle(
        TextLabelStyle(),
        null,
        ButtonStyle(),
        ContainerStyle(),
    ),
    val containerStyle: ContainerStyle = ContainerStyle(),
    val isOptional: Boolean = false,
)
