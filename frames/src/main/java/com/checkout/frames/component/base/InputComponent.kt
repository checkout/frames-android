package com.checkout.frames.component.base

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.checkout.frames.mapper.ContainerStyleToModifierMapper
import com.checkout.frames.mapper.ImageStyleToComposableImageMapper
import com.checkout.frames.mapper.InputComponentStyleToStateMapper
import com.checkout.frames.mapper.InputComponentStyleToViewStyleMapper
import com.checkout.frames.mapper.InputFieldStyleToViewStyleMapper
import com.checkout.frames.mapper.TextLabelStyleToStateMapper
import com.checkout.frames.mapper.TextLabelStyleToViewStyleMapper
import com.checkout.frames.model.CornerRadius
import com.checkout.frames.model.Margin
import com.checkout.frames.model.Padding
import com.checkout.frames.model.Shape
import com.checkout.frames.model.font.Font
import com.checkout.frames.style.component.base.InputComponentStyle
import com.checkout.frames.style.component.base.TextLabelStyle
import com.checkout.frames.style.component.base.TextStyle
import com.checkout.frames.style.component.base.ImageStyle
import com.checkout.frames.style.component.base.InputFieldStyle
import com.checkout.frames.style.component.base.ContainerStyle
import com.checkout.frames.style.component.base.InputFieldIndicatorStyle
import com.checkout.frames.style.view.InputComponentViewStyle
import com.checkout.frames.view.InputField
import com.checkout.frames.view.TextLabel

@Composable
internal fun InputComponent(
    style: InputComponentViewStyle,
    state: InputComponentState,
    onFocusChanged: ((Boolean) -> Unit)? = null,
    onValueChange: (String) -> Unit
) = with(state) {
    Column(modifier = style.containerModifier.wrapContentHeight()) {
        Row {
            Column(
                modifier = Modifier
                    .weight(1F)
                    .wrapContentHeight()
            ) {
                // Title label
                if (titleState.isVisible.value) TextLabel(style.titleStyle, titleState)
                // Subtitle label
                if (subtitleState.isVisible.value) TextLabel(style.subtitleStyle, subtitleState)
            }
            Column(
                Modifier
                    .align(Alignment.Bottom)
                    .wrapContentHeight()
            ) {
                // Info label
                if (infoState.isVisible.value) TextLabel(style.infoStyle, infoState)
            }
        }
        // Input field
        InputField(style.inputFieldStyle, inputFieldState, onFocusChanged, onValueChange)
        // Error message
        if (errorState.isVisible.value) TextLabel(style.errorMessageStyle, errorState)
    }
}

/* ------------------------------ Preview ------------------------------ */

@SuppressWarnings("LongMethod", "MagicNumber")
@Preview(showBackground = true, name = "RoundCorner InputComponent")
@Composable
private fun RoundCornerInputComponentPreview() {
    val containerMapper = ContainerStyleToModifierMapper()
    val textStyleMapper = TextLabelStyleToViewStyleMapper(containerMapper)
    val inputFieldMapper = InputFieldStyleToViewStyleMapper(textStyleMapper)
    val inputComponentStyleMapper =
        InputComponentStyleToViewStyleMapper(textStyleMapper, inputFieldMapper, containerMapper)
    val imageMapper = ImageStyleToComposableImageMapper()
    val textLabelMapper = TextLabelStyleToStateMapper(imageMapper)
    val inputComponentStateMapper = InputComponentStyleToStateMapper(imageMapper, textLabelMapper)

    val componentState: InputComponentState

    val style = InputComponentStyle(
        titleStyle = TextLabelStyle(
            "Title",
            textStyle = TextStyle(size = 15, color = 0xFF141414, font = Font.SansSerif, maxLines = 1),
            leadingIconStyle = ImageStyle(
                android.R.drawable.ic_menu_report_image,
                0xFF00FF00,
                24,
                24,
                Padding(end = 8)
            ),
            trailingIconStyle = ImageStyle(
                android.R.drawable.ic_dialog_map,
                0xFFFF00FF,
                32, 32,
                Padding(start = 8)
            ),
        ),
        subtitleStyle = TextLabelStyle(
            "Subtitle",
            textStyle = TextStyle(size = 13, color = 0xFF636363, font = Font.SansSerif, maxLines = 1),
            leadingIconStyle = ImageStyle(
                android.R.drawable.ic_menu_report_image,
                0xFF00FF00,
                13,
                13,
                Padding(end = 8)
            ),
            trailingIconStyle = ImageStyle(android.R.drawable.ic_dialog_map, 0xFFFF00FF, 13, 13, Padding(start = 8)),
        ),
        infoStyle = TextLabelStyle(
            "Optional",
            textStyle = TextStyle(size = 13, color = 0xFF636363, font = Font.SansSerif, maxLines = 1),
            leadingIconStyle = ImageStyle(
                android.R.drawable.ic_menu_report_image,
                0xFF00FF00,
                13,
                13,
                Padding(end = 8)
            ),
            trailingIconStyle = ImageStyle(android.R.drawable.ic_dialog_map, 0xFFFF00FF, 13, 13, Padding(start = 8)),
        ),
        inputFieldStyle = InputFieldStyle(
            textStyle = TextStyle(size = 16, color = 0xFF141414, font = Font.SansSerif, maxLines = 1),
            placeholderText = "Placeholder text",
            placeholderStyle = TextStyle(size = 16, color = 0xFF636363, font = Font.SansSerif, maxLines = 1),
            containerStyle = ContainerStyle(margin = Margin(top = 8, bottom = 8)),
            indicatorStyle = InputFieldIndicatorStyle.Border(
                unfocusedBorderColor = 0xFF8A8A8A,
                focusedBorderColor = 0xFF0B5FF0,
                errorBorderColor = 0xFFAD283E
            ),
            leadingIconStyle = ImageStyle(android.R.drawable.ic_input_get, 0xFF000000, 16, 16, Padding(end = 8)),
            trailingIconStyle = ImageStyle(
                android.R.drawable.ic_input_get,
                0xFF000000,
                width = 13,
                height = 13,
                padding = Padding(start = 8)
            ),
        ),
        errorMessageStyle = TextLabelStyle(
            "",
            textStyle = TextStyle(size = 13, color = 0xFFAD283E, font = Font.SansSerif),
            leadingIconStyle = ImageStyle(android.R.drawable.ic_dialog_info, 0xFFFF0000, 13, 13, Padding(end = 8)),
            trailingIconStyle = ImageStyle(android.R.drawable.ic_dialog_info, 0xFFFF0000, 13, 13, Padding(start = 8)),
        ),
        containerStyle = ContainerStyle(margin = Margin(16, 16, 16, 16))
    )

    componentState = inputComponentStateMapper.map(style)

    InputComponent(
        style = inputComponentStyleMapper.map(style),
        state = componentState
    ) {
        componentState.inputFieldState.text.value = it
        with(componentState) {
            val isError = it == "qwerty"

            inputFieldState.isError.value = isError
            errorState.text.value = if (isError) "Error message" else ""
            errorState.isVisible.value = isError
        }
    }
}

@SuppressWarnings("MagicNumber")
@Preview(showBackground = true, name = "Underline InputComponent")
@Composable
private fun UnderlineInputComponentPreview() {
    val containerMapper = ContainerStyleToModifierMapper()
    val textStyleMapper = TextLabelStyleToViewStyleMapper(containerMapper)
    val inputFieldMapper = InputFieldStyleToViewStyleMapper(textStyleMapper)
    val inputComponentStyleMapper =
        InputComponentStyleToViewStyleMapper(textStyleMapper, inputFieldMapper, containerMapper)
    val imageMapper = ImageStyleToComposableImageMapper()
    val textLabelMapper = TextLabelStyleToStateMapper(imageMapper)
    val inputComponentStateMapper = InputComponentStyleToStateMapper(imageMapper, textLabelMapper)

    val componentState: InputComponentState

    val style = InputComponentStyle(
        titleStyle = TextLabelStyle(
            "Title".uppercase(),
            textStyle = TextStyle(size = 15, color = 0xFF141414, font = Font.SansSerif, maxLines = 1)
        ),
        infoStyle = TextLabelStyle(
            "Optional",
            textStyle = TextStyle(size = 13, color = 0xFF636363, font = Font.SansSerif, maxLines = 1)
        ),
        inputFieldStyle = InputFieldStyle(
            textStyle = TextStyle(size = 16, color = 0xFF141414, font = Font.SansSerif, maxLines = 1),
            placeholderText = "Placeholder text",
            placeholderStyle = TextStyle(size = 16, color = 0xFF636363, font = Font.SansSerif, maxLines = 1),
            containerStyle = ContainerStyle(margin = Margin(top = 8, bottom = 8)),
            indicatorStyle = InputFieldIndicatorStyle.Underline(
                unfocusedUnderlineColor = 0xFF8A8A8A,
                focusedUnderlineColor = 0xFF0B5FF0,
                errorUnderlineColor = 0xFFAD283E
            )
        ),
        errorMessageStyle = TextLabelStyle(
            "",
            textStyle = TextStyle(size = 13, color = 0xFFAD283E, font = Font.SansSerif)
        ),
        containerStyle = ContainerStyle(margin = Margin(16, 16, 16, 16))
    )

    componentState = inputComponentStateMapper.map(style)

    InputComponent(
        style = inputComponentStyleMapper.map(style),
        state = componentState
    ) {
        componentState.inputFieldState.text.value = it
        with(componentState) {
            val isError = it == "qwerty"
            inputFieldState.isError.value = isError
            errorState.text.value = if (isError) "Error message" else ""
            errorState.isVisible.value = isError
        }
    }
}

@SuppressWarnings("MagicNumber")
@Preview(showBackground = true, backgroundColor = 0xFF17201E, name = "Background InputComponent")
@Composable
private fun BackgroundInputComponentPreview() {
    val containerMapper = ContainerStyleToModifierMapper()
    val textStyleMapper = TextLabelStyleToViewStyleMapper(containerMapper)
    val inputFieldMapper = InputFieldStyleToViewStyleMapper(textStyleMapper)
    val inputComponentStyleMapper =
        InputComponentStyleToViewStyleMapper(textStyleMapper, inputFieldMapper, containerMapper)
    val imageMapper = ImageStyleToComposableImageMapper()
    val textLabelMapper = TextLabelStyleToStateMapper(imageMapper)
    val inputComponentStateMapper = InputComponentStyleToStateMapper(imageMapper, textLabelMapper)

    val componentState: InputComponentState

    val style = InputComponentStyle(
        titleStyle = TextLabelStyle(
            "Title".uppercase(),
            textStyle = TextStyle(size = 15, color = 0xFF00CC2D, font = Font.SansSerif, maxLines = 1)
        ),
        inputFieldStyle = InputFieldStyle(
            textStyle = TextStyle(size = 16, color = 0xFF00CC2D, font = Font.SansSerif, maxLines = 1),
            placeholderText = "__ / __",
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
            )
        ),
        errorMessageStyle = TextLabelStyle(
            "",
            textStyle = TextStyle(size = 13, color = 0xFFAD283E, font = Font.SansSerif)
        ),
        containerStyle = ContainerStyle(margin = Margin(16, 16, 16, 16))
    )

    componentState = inputComponentStateMapper.map(style)

    InputComponent(
        style = inputComponentStyleMapper.map(style),
        state = componentState
    ) {
        componentState.inputFieldState.text.value = it
        with(componentState) {
            val isError = it == "qwerty"
            inputFieldState.isError.value = isError
            errorState.text.value = if (isError) "Error message" else ""
            errorState.isVisible.value = isError
        }
    }
}
