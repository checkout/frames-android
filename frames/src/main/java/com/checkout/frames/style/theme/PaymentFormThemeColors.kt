package com.checkout.frames.style.theme

import com.checkout.frames.style.theme.colors.PaymentFormColors
import com.checkout.frames.style.theme.colors.PaymentFormButtonColors
import com.checkout.frames.style.theme.colors.CursorColors
import com.checkout.frames.style.theme.colors.DividerColor
import com.checkout.frames.style.theme.colors.ImageColors
import com.checkout.frames.style.theme.colors.TextColors
import com.checkout.frames.style.theme.colors.InputFieldColors

public data class PaymentFormThemeColors(
    val paymentFormColors: PaymentFormColors,
    val buttonColors: PaymentFormButtonColors,
    val cursorColors: CursorColors,
    val dividerColor: DividerColor,
    val imageColors: ImageColors,
    val textColors: TextColors,
    val inputFieldColors: InputFieldColors
)
