package com.checkout.frames.style.view.addresssummary

import androidx.compose.ui.Modifier
import com.checkout.frames.style.view.DividerViewStyle
import com.checkout.frames.style.view.InternalButtonViewStyle
import com.checkout.frames.style.view.TextLabelViewStyle

internal data class AddressSummarySectionViewStyle(
    val addressTextStyle: TextLabelViewStyle,
    val dividerStyle: DividerViewStyle? = null,
    val editAddressButtonStyle: InternalButtonViewStyle,
    val modifier: Modifier,
)
