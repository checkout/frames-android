package com.checkout.frames.style.theme

import androidx.annotation.ColorLong
import com.checkout.frames.style.theme.colors.CursorColors
import com.checkout.frames.style.theme.colors.DividerColor
import com.checkout.frames.style.theme.colors.ImageColors
import com.checkout.frames.style.theme.colors.InputFieldColors
import com.checkout.frames.style.theme.colors.PaymentFormButtonColors
import com.checkout.frames.style.theme.colors.PaymentFormColors
import com.checkout.frames.style.theme.colors.TextColors

public data class PaymentFormThemeColors(
    val paymentFormColors: PaymentFormColors,
    val buttonColors: PaymentFormButtonColors,
    val cursorColors: CursorColors,
    val dividerColor: DividerColor,
    val imageColors: ImageColors,
    val textColors: TextColors,
    val inputFieldColors: InputFieldColors,
) {
    public constructor(
        @ColorLong
        accentColor: Long,
        @ColorLong
        textColor: Long,
        @ColorLong
        errorColor: Long,
        @ColorLong
        backgroundColor: Long,
        @ColorLong
        fieldBackgroundColor: Long,
        @ColorLong
        enabledButtonColor: Long,
        @ColorLong
        disabledButtonColor: Long,
    ) : this(
        paymentFormColors = PaymentFormColors(background = backgroundColor),
        buttonColors = PaymentFormButtonColors(
            containerColor = accentColor,
            disabledContainerColor = disabledButtonColor,
            contentColor = enabledButtonColor,
            disabledContentColor = textColor,
        ),
        cursorColors = CursorColors(
            cursorColor = accentColor,
            errorCursorColor = errorColor,
            cursorHandleColor = accentColor,
            cursorHighlightColor = backgroundColor,
        ),
        dividerColor = DividerColor(color = backgroundColor),
        imageColors = ImageColors(tinColor = accentColor),
        textColors = TextColors(
            titleColor = accentColor,
            subTitleColor = textColor,
            infoColor = textColor,
            errorColor = errorColor,
        ),
        inputFieldColors = InputFieldColors(
            focusedBorderColor = accentColor,
            unfocusedBorderColor = textColor,
            disabledBorderColor = textColor,
            errorBorderColor = errorColor,
            inputFieldBackgroundColor = fieldBackgroundColor,
        ),
    )
}
