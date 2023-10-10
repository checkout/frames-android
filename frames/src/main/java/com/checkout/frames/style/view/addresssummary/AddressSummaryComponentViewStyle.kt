package com.checkout.frames.style.view.addresssummary

import androidx.compose.ui.Modifier
import com.checkout.frames.style.view.InternalButtonViewStyle
import com.checkout.frames.style.view.TextLabelViewStyle

internal data class AddressSummaryComponentViewStyle(
    val titleStyle: TextLabelViewStyle?,
    val subTitleStyle: TextLabelViewStyle?,
    val addAddressButtonStyle: InternalButtonViewStyle,
    val summarySectionStyle: AddressSummarySectionViewStyle,
    val modifier: Modifier,
)
