package com.checkout.frames.utils.extensions

import com.checkout.frames.model.Padding
import com.checkout.frames.style.component.base.TextLabelStyle
import com.checkout.frames.style.component.base.TextStyle
import com.checkout.frames.style.component.base.InputFieldIndicatorStyle
import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.style.component.base.InputFieldStyle
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.ButtonStyle
import com.checkout.frames.style.theme.PaymentFormComponent
import com.checkout.frames.style.theme.PaymentFormTheme

internal fun TextLabelStyle?.provideTitleStyle(
    component: PaymentFormComponent,
    from: PaymentFormTheme
): TextLabelStyle? {
    return this?.copy(
        text = text.provideText(component.titleText),
        textId = textId?.provideTextId(component.titleTextId),
        leadingIconStyle = leadingIconStyle?.copy(
            tinColor = from.paymentFormThemeColors.imageColors.tinColor
        ),
        textStyle = provideTitleTextStyle(from)
    )
}

internal fun TextLabelStyle?.provideTitleTextStyle(paymentFormTheme: PaymentFormTheme): TextStyle {
    return this?.textStyle?.copy(color = paymentFormTheme.paymentFormThemeColors.textColors.titleColor)
        ?: TextStyle()
}

internal fun String.provideText(text: String? = null): String {
    return if (text.isNullOrEmpty()) this else text
}

internal fun Int.provideTextId(textID: Int? = null): Int {
    return textID ?: this
}

internal fun TextLabelStyle?.provideSubTitleStyle(
    component: PaymentFormComponent,
    from: PaymentFormTheme
): TextLabelStyle? {
    return this?.copy(
        text = text.provideText(component.subTitleText),
        textId = textId?.provideTextId(component.subTitleTextId),
        textStyle = provideSubTitleTextStyle(from)
    )
}

internal fun TextLabelStyle?.provideInfoStyle(
    component: PaymentFormComponent,
    from: PaymentFormTheme
): TextLabelStyle? {
    return this?.copy(
        text = text.provideText(component.infoText),
        textId = textId?.provideTextId(component.infoTextId),
        textStyle = provideInfoTextStyle(from)
    )
}

internal fun TextLabelStyle?.provideSubTitleTextStyle(
    paymentFormTheme: PaymentFormTheme
): TextStyle {
    return this?.textStyle?.copy(color = paymentFormTheme.paymentFormThemeColors.textColors.subTitleColor)
        ?: TextStyle()
}

internal fun TextLabelStyle?.provideInfoTextStyle(
    paymentFormTheme: PaymentFormTheme
): TextStyle {
    return this?.textStyle?.copy(color = paymentFormTheme.paymentFormThemeColors.textColors.infoColor)
        ?: TextStyle()
}

internal fun TextLabelStyle?.provideErrorMessageStyle(
    paymentFormTheme: PaymentFormTheme
): TextLabelStyle? {
    return this?.copy(
        textStyle = this.textStyle.copy(
            color = paymentFormTheme.paymentFormThemeColors.textColors.errorColor
        )
    )
}

internal fun PaymentFormTheme.provideIndicatorStyle(): InputFieldIndicatorStyle {
    return InputFieldIndicatorStyle.Border().copy(
        focusedBorderColor = paymentFormThemeColors.inputFieldColors.focusedBorderColor,
        unfocusedBorderColor = paymentFormThemeColors.inputFieldColors.unfocusedBorderColor,
        disabledBorderColor = paymentFormThemeColors.inputFieldColors.disabledBorderColor,
        errorBorderColor = paymentFormThemeColors.inputFieldColors.errorBorderColor
    )
}

internal fun InputComponentStyle.provideInputFieldStyle(from: PaymentFormTheme): InputFieldStyle {
    return inputFieldStyle.copy(
        indicatorStyle = from.provideIndicatorStyle(),
        textStyle = titleStyle.provideTitleTextStyle(from),
        trailingIconStyle = inputFieldStyle.trailingIconStyle?.copy(
            tinColor = from.paymentFormThemeColors.imageColors.tinColor
        ),
        cursorStyle = inputFieldStyle.cursorStyle?.copy(
            cursorColor = from.paymentFormThemeColors.cursorColors.cursorColor,
            cursorHandleColor = from.paymentFormThemeColors.cursorColors.cursorHandleColor,
            errorCursorColor = from.paymentFormThemeColors.cursorColors.errorCursorColor,
            cursorHighlightColor = from.paymentFormThemeColors.cursorColors.cursorHighlightColor
        ),
        containerStyle = inputFieldStyle.containerStyle.provideInputFieldContainerStyle(from)
    )
}

internal fun ContainerStyle.provideInputFieldContainerStyle(
    from: PaymentFormTheme
): ContainerStyle {
    return copy(
        color = from.paymentFormThemeColors.inputFieldColors.inputFieldBackgroundColor
    )
}

internal fun PaymentFormTheme.provideContainerStyle(padding: Padding? = null): ContainerStyle {
    return ContainerStyle().copy(
        color = paymentFormThemeColors.paymentFormColors.background,
        padding = padding
    )
}

internal fun ButtonStyle.provideButtonStyle(
    from: PaymentFormTheme,
    component: PaymentFormComponent
): ButtonStyle {
    return copy(
        contentColor = from.paymentFormThemeColors.buttonColors.contentColor,
        disabledContentColor = from.paymentFormThemeColors.buttonColors.disabledContentColor,
        containerColor = from.paymentFormThemeColors.buttonColors.containerColor,
        disabledContainerColor = from.paymentFormThemeColors.buttonColors.disabledContainerColor,
        textStyle = textStyle.copy(
            text = textStyle.text.provideText(component.titleText),
            textId = textStyle.textId?.provideTextId(component.titleTextId),
            trailingIconStyle = textStyle.trailingIconStyle?.copy(
                tinColor = from.paymentFormThemeColors.buttonColors.contentColor
            ),
            textStyle = textStyle.provideTitleTextStyle(from)
        )
    )
}
