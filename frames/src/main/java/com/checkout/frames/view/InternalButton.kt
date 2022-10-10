package com.checkout.frames.view

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.checkout.frames.R
import com.checkout.frames.mapper.ImageStyleToComposableImageMapper
import com.checkout.frames.mapper.ButtonStyleToInternalStateMapper
import com.checkout.frames.mapper.ButtonStyleToInternalViewStyleMapper
import com.checkout.frames.mapper.ContainerStyleToModifierMapper
import com.checkout.frames.mapper.TextLabelStyleToStateMapper
import com.checkout.frames.mapper.TextLabelStyleToViewStyleMapper
import com.checkout.frames.model.Shape
import com.checkout.frames.model.CornerRadius
import com.checkout.frames.model.BorderStroke
import com.checkout.frames.model.Padding
import com.checkout.frames.model.ButtonElevation
import com.checkout.frames.model.Margin
import com.checkout.frames.model.font.Font
import com.checkout.frames.style.component.base.ButtonStyle
import com.checkout.frames.style.component.base.TextStyle
import com.checkout.frames.style.component.base.ImageStyle
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.default.DefaultTextLabelStyle
import com.checkout.frames.style.view.InternalButtonViewStyle

@Composable
internal fun InternalButton(
    style: InternalButtonViewStyle,
    state: InternalButtonState,
    onClick: () -> Unit
) = with(style) {
    style.textStyle.color = Color.Unspecified

    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = state.isEnabled.value,
        elevation = provideButtonElevation(style),
        shape = shape,
        border = border,
        colors = provideColors(style),
        contentPadding = contentPadding
    ) {
        TextLabel(style = style.textStyle, state = state.textState)
    }
}

@Composable
private fun provideButtonElevation(style: InternalButtonViewStyle) = ButtonDefaults.buttonElevation(
    style.defaultElevation,
    style.pressedElevation,
    style.focusedElevation,
    style.hoveredElevation
)

@Composable
private fun provideColors(style: InternalButtonViewStyle) = ButtonDefaults.buttonColors(
    containerColor = style.containerColor,
    contentColor = style.contentColor,
    disabledContainerColor = style.disabledContainerColor,
    disabledContentColor = style.disabledContentColor
)

@SuppressWarnings("MagicNumber")
@Preview(showBackground = true, name = "OutlineButtonPreview")
@Composable
private fun OutlineButtonPreview() {
    val containerMapper = ContainerStyleToModifierMapper()
    val styleMapper = ButtonStyleToInternalViewStyleMapper(
        containerMapper,
        TextLabelStyleToViewStyleMapper(containerMapper)
    )
    val stateMapper = ButtonStyleToInternalStateMapper(
        TextLabelStyleToStateMapper(
            imageMapper = ImageStyleToComposableImageMapper()
        )
    )

    val style = ButtonStyle(
        containerColor = 0x00000000,
        disabledContainerColor = 0xFFCCCCCC,
        contentColor = 0xFF0B5FF0,
        disabledContentColor = 0xFFFFFF00,
        shape = Shape.RoundCorner,
        cornerRadius = CornerRadius(8),
        borderStroke = BorderStroke(1, 0xFF8099FD),
        contentPadding = Padding(start = 16, end = 16, top = 8, bottom = 8),
        textStyle = DefaultTextLabelStyle.title().copy(
            textStyle = TextStyle(size = 15, font = Font.SansSerif),
            text = "Add billing address",
            trailingIconStyle = ImageStyle(
                image = R.drawable.cko_ic_caret_right,
                tinColor = 0xFF0B5FF0,
                height = 12,
                padding = Padding(start = 12)
            )
        ),
        containerStyle = ContainerStyle(margin = Margin(16))
    )

    val state = stateMapper.map(style)

    InternalButton(
        styleMapper.map(style).apply { textStyle.textMaxWidth = true },
        state
    ) { state.isEnabled.value = !state.isEnabled.value }
}

@SuppressWarnings("MagicNumber")
@Preview(showBackground = true, name = "OutlineButtonPreview")
@Composable
private fun ButtonPreview() {
    val containerMapper = ContainerStyleToModifierMapper()
    val styleMapper = ButtonStyleToInternalViewStyleMapper(
        containerMapper,
        TextLabelStyleToViewStyleMapper(containerMapper)
    )
    val stateMapper = ButtonStyleToInternalStateMapper(
        TextLabelStyleToStateMapper(
            imageMapper = ImageStyleToComposableImageMapper()
        )
    )

    val style = ButtonStyle(
        containerColor = 0xFF0B5FF0,
        disabledContainerColor = 0xFFCCCCCC,
        contentColor = 0xFFFFFFFF,
        disabledContentColor = 0xFF000000,
        shape = Shape.RoundCorner,
        cornerRadius = CornerRadius(8),
        contentPadding = Padding(start = 16, end = 16, top = 8, bottom = 8),
        textStyle = DefaultTextLabelStyle.title().copy(
            textStyle = TextStyle(size = 15, font = Font.SansSerif),
            text = "Save"
        ),
        buttonElevation = ButtonElevation(defaultElevation = 8),
        containerStyle = ContainerStyle(margin = Margin(16))
    )

    val state = stateMapper.map(style)

    InternalButton(
        styleMapper.map(style),
        state
    ) { state.isEnabled.value = !state.isEnabled.value }
}
