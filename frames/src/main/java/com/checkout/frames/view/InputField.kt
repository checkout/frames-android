package com.checkout.frames.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors
import androidx.compose.material3.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.checkout.frames.model.InputFieldColors
import com.checkout.frames.utils.BorderConstants
import com.checkout.frames.style.view.InputFieldViewStyle

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun InputField(
    style: InputFieldViewStyle,
    state: InputFieldState,
    onValueChange: (String) -> Unit
) = with(style) {
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }
    val textStyle = textStyle ?: LocalTextStyle.current
    val colors = provideInputFieldColors(borderShape != null, colors)
    // If color is not provided via the text style, use content color as a default
    val textColor = textStyle.color.takeOrElse { colors.textColor(enabled).value }
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))
    var modifier = modifier
        .background(colors.containerColor(enabled).value, containerShape)
        .defaultMinSize(
            minWidth = TextFieldDefaults.MinWidth,
            minHeight = TextFieldDefaults.MinHeight
        )

    modifier = if (borderShape == null) modifier.indicatorLine(
        enabled,
        state.isError.value,
        interactionSource,
        colors,
        focusedIndicatorLineThickness = focusedBorderThickness,
        unfocusedIndicatorLineThickness = unfocusedBorderThickness
    ) else modifier

    BasicTextField(
        value = state.text.value,
        modifier = modifier,
        onValueChange = onValueChange.withMaxLength(maxLength),
        enabled = enabled,
        readOnly = readOnly,
        textStyle = mergedTextStyle,
        cursorBrush = SolidColor(colors.cursorColor(state.isError.value).value),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        singleLine = singleLine,
        maxLines = maxLines,
        decorationBox = @Composable { innerTextField ->
            DecorationBox(
                value = state.text.value,
                visualTransformation = visualTransformation,
                innerTextField = innerTextField,
                placeholder = placeholder,
                leadingIcon = state.leadingIcon.value,
                trailingIcon = state.trailingIcon.value,
                singleLine = singleLine,
                enabled = enabled,
                isError = state.isError.value,
                interactionSource = interactionSource,
                colors = colors,
                borderShape = borderShape,
                focusedBorderThickness = focusedBorderThickness,
                unfocusedBorderThickness = unfocusedBorderThickness
            )
        }
    )
}

@Composable
@SuppressWarnings("LongParameterList")
@OptIn(ExperimentalMaterial3Api::class)
private fun DecorationBox(
    value: String,
    visualTransformation: VisualTransformation,
    innerTextField: @Composable () -> Unit,
    placeholder: @Composable (() -> Unit)?,
    leadingIcon: @Composable (() -> Unit)?,
    trailingIcon: @Composable (() -> Unit)?,
    singleLine: Boolean,
    enabled: Boolean,
    isError: Boolean,
    interactionSource: MutableInteractionSource,
    colors: TextFieldColors,
    borderShape: Shape?,
    focusedBorderThickness: Dp,
    unfocusedBorderThickness: Dp
) {
    if (borderShape == null) TextFieldDefaults.TextFieldDecorationBox(
        value = value,
        visualTransformation = visualTransformation,
        innerTextField = innerTextField,
        placeholder = placeholder,
        label = null,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        singleLine = singleLine,
        enabled = enabled,
        isError = isError,
        interactionSource = interactionSource,
        colors = colors
    )
    else TextFieldDefaults.OutlinedTextFieldDecorationBox(
        value = value,
        visualTransformation = visualTransformation,
        innerTextField = innerTextField,
        placeholder = placeholder,
        label = null,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        singleLine = singleLine,
        enabled = enabled,
        isError = isError,
        interactionSource = interactionSource,
        colors = colors,
        border = {
            TextFieldDefaults.BorderBox(
                enabled,
                isError,
                interactionSource,
                colors,
                borderShape,
                focusedBorderThickness = focusedBorderThickness,
                unfocusedBorderThickness = unfocusedBorderThickness
            )
        }
    )
}

@Composable
@SuppressWarnings("NestedBlockDepth")
private fun provideInputFieldColors(withBorder: Boolean, colors: InputFieldColors?): TextFieldColors {
    return if (withBorder) outlinedTextFieldColors(
        textColor = colors?.textColor ?: Color.Black,
        placeholderColor = colors?.placeholderColor ?: Color.Gray,
        focusedBorderColor = colors?.focusedIndicatorColor ?: Color(BorderConstants.focusedBorderColor),
        unfocusedBorderColor = colors?.unfocusedIndicatorColor ?: Color(BorderConstants.unfocusedBorderColor),
        errorBorderColor = colors?.errorIndicatorColor ?: Color(BorderConstants.errorBorderColor),
        containerColor = colors?.containerColor ?: Color.Transparent
    ).apply {
        colors?.let { colors ->
            colors.textColor?.let { }
        }
    } else textFieldColors(
        textColor = colors?.textColor ?: Color.Black,
        placeholderColor = colors?.placeholderColor ?: Color.Gray,
        focusedIndicatorColor = colors?.focusedIndicatorColor ?: Color(BorderConstants.focusedBorderColor),
        unfocusedIndicatorColor = colors?.unfocusedIndicatorColor ?: Color(BorderConstants.unfocusedBorderColor),
        errorIndicatorColor = colors?.errorIndicatorColor ?: Color(BorderConstants.errorBorderColor),
        containerColor = colors?.containerColor ?: Color.Transparent
    )
}

private fun ((String) -> Unit).withMaxLength(maxLength: Int?): (String) -> Unit = {
    maxLength?.let { limit ->
        if (it.length <= limit) this(it)
    } ?: run { this(it) }
}

/* ------------------------------ Preview ------------------------------ */

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true, name = "Default InputField")
@Composable
private fun InputFieldPreview() {
    val text = mutableStateOf("")

    Surface(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.background
    ) {
        InputField(
            InputFieldViewStyle(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = InputFieldColors(containerColor = Color.Transparent),
                placeholder = { Text(text = "Test placeholder") },
                maxLength = 8
            ),
            InputFieldState(text)
        ) { text.value = it }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true, name = "Round InputField")
@Composable
private fun RoundInputFieldPreview() {
    val text = mutableStateOf("")

    Surface(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.background
    ) {
        InputField(
            InputFieldViewStyle(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = InputFieldColors(
                    containerColor = Color.Gray,
                    placeholderColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                placeholder = { Text(text = "Test placeholder") },
                containerShape = RoundedCornerShape(12.dp),
                maxLength = 8
            ),
            InputFieldState(text)
        ) { text.value = it }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true, name = "Default OutlineInputField")
@Composable
private fun OutlineInputFieldPreview() {
    val text = mutableStateOf("")

    Surface(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.background
    ) {
        InputField(
            InputFieldViewStyle(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                placeholder = { Text(text = "Test placeholder Outline") },
                maxLength = 8,
                borderShape = RoundedCornerShape(8.dp)
            ),
            InputFieldState(text)
        ) { text.value = it }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true, name = "Rectangle OutlineInputField")
@Composable
private fun CustomOutlineInputFieldPreview() {
    val text = mutableStateOf("")

    Surface(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.background
    ) {
        InputField(
            InputFieldViewStyle(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                placeholder = { Text(text = "Test placeholder Outline") },
                maxLength = 8,
                borderShape = RectangleShape
            ),
            InputFieldState(text)
        ) { text.value = it }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true, name = "Cut corner OutlineInputField")
@Composable
private fun CutCornerOutlineInputFieldPreview() {
    val text = mutableStateOf("")

    Surface(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.background
    ) {
        InputField(
            InputFieldViewStyle(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                placeholder = { Text(text = "Test placeholder Outline") },
                maxLength = 8,
                colors = InputFieldColors(unfocusedIndicatorColor = Color.Blue),
                borderShape = CutCornerShape(16.dp),
                unfocusedBorderThickness = 4.dp
            ),
            InputFieldState(text)
        ) { text.value = it }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true, name = "Circle OutlineInputField1")
@Composable
private fun CircleOutlineInputFieldPreview() {
    val text = mutableStateOf("")

    Surface(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.background
    ) {
        InputField(
            InputFieldViewStyle(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                placeholder = { Text(text = "Test placeholder Outline") },
                maxLength = 8,
                colors = InputFieldColors(unfocusedIndicatorColor = Color.Magenta),
                borderShape = CircleShape,
                unfocusedBorderThickness = 2.dp
            ),
            InputFieldState(text)
        ) { text.value = it }
    }
}
