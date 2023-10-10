package com.checkout.frames.component.addresssummary

import com.checkout.frames.view.InternalButtonState
import com.checkout.frames.view.TextLabelState

internal data class AddressSummaryComponentState(
    val titleState: TextLabelState,
    val subTitleState: TextLabelState,
    val addressPreviewState: TextLabelState,
    val editAddressButtonState: InternalButtonState,
    val addAddressButtonState: InternalButtonState,
)
