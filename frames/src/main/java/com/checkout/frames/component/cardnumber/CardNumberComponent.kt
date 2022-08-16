package com.checkout.frames.component.cardnumber

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.checkout.frames.component.base.InputComponent
import com.checkout.frames.di.base.Injector
import com.checkout.frames.di.injector.FramesInjector
import com.checkout.frames.model.CornerRadius
import com.checkout.frames.model.Margin
import com.checkout.frames.model.Padding
import com.checkout.frames.model.Shape
import com.checkout.frames.model.font.Font
import com.checkout.frames.style.component.CardNumberComponentStyle
import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.style.component.base.InputFieldStyle
import com.checkout.frames.style.component.base.TextLabelStyle
import com.checkout.frames.style.component.base.TextStyle
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.InputFieldIndicatorStyle
import com.checkout.frames.style.component.base.ImageStyle
import com.checkout.frames.style.component.base.CursorStyle

@Composable
internal fun CardNumberComponent(
    style: CardNumberComponentStyle,
    injector: Injector
) {
    val viewModel: CardNumberViewModel = viewModel(
        factory = CardNumberViewModel.Factory(injector, style)
    )

    InputComponent(
        style = viewModel.componentStyle,
        state = viewModel.componentState.inputState,
        onFocusChanged = viewModel::onFocusChanged,
        onValueChange = viewModel::onCardNumberChange
    )
}

@SuppressWarnings("LongMethod", "MagicNumber")
@Preview(showBackground = true, backgroundColor = 0xFF17201E, name = "DarkBorderlessCardNumberComponentPreview")
@Composable
private fun DarkBorderlessCardNumberComponentPreview() {
    val style = InputComponentStyle(
        titleStyle = TextLabelStyle(
            "Card number",
            textStyle = TextStyle(size = 15, color = 0xFF00CC2D, font = Font.SansSerif, maxLines = 1)
        ),
        inputFieldStyle = InputFieldStyle(
            textStyle = TextStyle(size = 16, color = 0xFFFFFFFF, font = Font.SansSerif, maxLines = 1),
            placeholderText = "XXXX XXXX XXXX XXXX",
            placeholderStyle = TextStyle(size = 16, color = 0x6000CC2D, font = Font.SansSerif, maxLines = 1),
            containerStyle = ContainerStyle(
                color = 0xFF24302D,
                shape = Shape.RoundCorner,
                cornerRadius = CornerRadius(8),
                margin = Margin(top = 8, bottom = 8),
            ),
            indicatorStyle = InputFieldIndicatorStyle.Underline(
                unfocusedUnderlineColor = 0x00000000,
                focusedUnderlineColor = 0x00000000,
                errorUnderlineColor = 0x00000000
            ),
            leadingIconStyle = ImageStyle(padding = Padding(start = 20, end = 10)),
            cursorStyle = CursorStyle(0xFF00CC2D, 0xFF00CC2D, 0xFF00CC2D, 0x4000CC2D)
        ),
        errorMessageStyle = TextLabelStyle(
            "",
            textStyle = TextStyle(size = 13, color = 0xFFAD283E, font = Font.SansSerif)
        )
    )

    Column(
        modifier = Modifier
            .background(Color(0xFF17201E))
            .padding(20.dp)
    ) {
        CardNumberComponent(
            CardNumberComponentStyle(style, ' '),
            FramesInjector.create()
        )
    }
}

@SuppressWarnings("LongMethod", "MagicNumber")
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, name = "LightWithBorderCardNumberComponentPreview")
@Composable
private fun LightWithBorderCardNumberComponentPreview() {
    val style = InputComponentStyle(
        titleStyle = TextLabelStyle(
            "Card number",
            textStyle = TextStyle(size = 15, color = 0xFF141414, font = Font.SansSerif, maxLines = 1),
        ),
        subtitleStyle = TextLabelStyle(
            "Amex, Diner's Club, Discover, JCB, Mada, Maestro, Mastercard and Visa accepted.",
            textStyle = TextStyle(size = 12, color = 0xFF636363, font = Font.SansSerif, maxLines = 2),
        ),
        inputFieldStyle = InputFieldStyle(
            textStyle = TextStyle(size = 16, color = 0xFF141414, font = Font.SansSerif, maxLines = 1),
            placeholderText = "XXXX XXXX XXXX XXXX",
            placeholderStyle = TextStyle(size = 16, color = 0x80636363, font = Font.SansSerif, maxLines = 1),
            containerStyle = ContainerStyle(margin = Margin(top = 8, bottom = 8)),
            indicatorStyle = InputFieldIndicatorStyle.Border(
                unfocusedBorderColor = 0xFF8A8A8A,
                focusedBorderColor = 0xFF0B5FF0,
                errorBorderColor = 0xFFAD283E
            ),
            leadingIconStyle = ImageStyle(padding = Padding(start = 20, end = 10), height = 36, width = 36)
        )
    )

    Column(modifier = Modifier.padding(20.dp)) {
        CardNumberComponent(
            CardNumberComponentStyle(style, ' '),
            FramesInjector.create()
        )

        CardNumberComponent(
            CardNumberComponentStyle(style, ' '),
            FramesInjector.create()
        )
    }
}
