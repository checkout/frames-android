package com.checkout.frames.utils.extensions

import com.checkout.frames.style.component.base.InputFieldIndicatorStyle

internal fun InputFieldIndicatorStyle.focusedIndicatorColor() = when (this) {
    is InputFieldIndicatorStyle.Underline -> this.focusedUnderlineColor
    is InputFieldIndicatorStyle.Border -> this.focusedBorderColor
}

internal fun InputFieldIndicatorStyle.unfocusedIndicatorColor() = when (this) {
    is InputFieldIndicatorStyle.Underline -> this.unfocusedUnderlineColor
    is InputFieldIndicatorStyle.Border -> this.unfocusedBorderColor
}

internal fun InputFieldIndicatorStyle.errorIndicatorColor() = when (this) {
    is InputFieldIndicatorStyle.Underline -> this.errorUnderlineColor
    is InputFieldIndicatorStyle.Border -> this.errorBorderColor
}
