package com.checkout.frames.style.theme

import com.checkout.frames.style.component.addresssummary.AddressSummaryComponentStyle
import com.checkout.frames.style.component.base.ButtonStyle
import com.checkout.frames.style.component.base.InputComponentStyle

internal data class RequestThemeStyles(
    val inputComponentStyle: InputComponentStyle? = null,
    val addressSummaryComponentStyle: AddressSummaryComponentStyle = AddressSummaryComponentStyle(),
    val buttonStyle: ButtonStyle = ButtonStyle()
)
