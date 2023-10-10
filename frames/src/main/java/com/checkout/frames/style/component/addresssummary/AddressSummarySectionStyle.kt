package com.checkout.frames.style.component.addresssummary

import com.checkout.frames.style.component.base.ButtonStyle
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.DividerStyle
import com.checkout.frames.style.component.base.TextLabelStyle

public data class AddressSummarySectionStyle @JvmOverloads constructor(
    val addressTextStyle: TextLabelStyle,
    val dividerStyle: DividerStyle? = null,
    val editAddressButtonStyle: ButtonStyle,
    val containerStyle: ContainerStyle,
)
