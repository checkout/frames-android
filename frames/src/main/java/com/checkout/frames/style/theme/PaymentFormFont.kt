package com.checkout.frames.style.theme

import com.checkout.frames.model.font.Font
import com.checkout.frames.model.font.FontStyle
import com.checkout.frames.model.font.FontWeight

public data class PaymentFormFont(
    /** Defines font family. */
    val font: Font = Font.Default,
    /** Defines font inputStyle, normal or italic. */
    val fontStyle: FontStyle = FontStyle.Normal,
    /** Defines font weight for the text field. */
    val fontWeight: FontWeight = FontWeight.Normal,
)
