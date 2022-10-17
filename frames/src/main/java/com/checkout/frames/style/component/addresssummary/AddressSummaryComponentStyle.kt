package com.checkout.frames.style.component.addresssummary

import com.checkout.frames.style.component.base.ButtonStyle
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.TextLabelStyle

public data class AddressSummaryComponentStyle(
    val titleStyle: TextLabelStyle? = null,
    val subTitleStyle: TextLabelStyle? = null,
    val addAddressButtonStyle: ButtonStyle,
    val summarySectionStyle: AddressSummarySectionStyle,
    val containerStyle: ContainerStyle = ContainerStyle()
) {
    public constructor() : this(
        summarySectionStyle = AddressSummarySectionStyle(
            TextLabelStyle(),
            null,
            ButtonStyle(),
            ContainerStyle()
        ),
        addAddressButtonStyle = ButtonStyle()
    )
}
