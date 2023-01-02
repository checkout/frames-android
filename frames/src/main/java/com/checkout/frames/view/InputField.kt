package com.checkout.frames.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors
import androidx.compose.material3.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillNode
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalAutofill
import androidx.compose.ui.platform.LocalAutofillTree
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.checkout.frames.model.InputFieldColors
import com.checkout.frames.style.view.InputFieldViewStyle
import com.checkout.frames.utils.constants.BorderConstants
import com.checkout.frames.utils.extensions.clearFocusOnKeyboardDismiss

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
internal fun InputField(
    style: InputFieldViewStyle,
    state: InputFieldState,
    autofillType: AutofillType? = null,
    onFocusChanged: ((Boolean) -> Unit)? = null,
    onValueChange: (String) -> Unit
) = with(style) {
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }
    val textStyle = provideTextStyle(this)
    val colors = provideInputFieldColors(borderShape != null, colors)
    // If color is not provided via the text inputStyle, use content color as a default
    val textColor = textStyle.color.takeOrElse { colors.textColor(enabled).value }
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))
    val textSelectionColors = provideTextSelectionColors(style.colors, colors.cursorColor(isError = false).value)

    var autofillNode: AutofillNode? = null
    if (autofillType != null) {
        autofillNode = AutofillNode(
            autofillTypes = listOf(autofillType),
            onFill = {
                state.text.value = it
            }
        )
        LocalAutofillTree.current += autofillNode
    }
    val autofill = LocalAutofill.current

    var modifier = modifier
        .clearFocusOnKeyboardDismiss()
        .background(colors.containerColor(enabled).value, containerShape)
        .onFocusChanged { onFocusChanged?.let { onFocusChanged -> onFocusChanged(it.isFocused) } }
        .defaultMinSize(
            minWidth = TextFieldDefaults.MinWidth,
            minHeight = TextFieldDefaults.MinHeight
        )
        .onGloballyPositioned {
            autofillNode?.boundingBox = it.boundsInWindow()
        }
        .onFocusChanged { focusState ->
            if (autofillNode != null) {
                autofill?.run {
                    if (focusState.isFocused) {
                        requestAutofillForNode(autofillNode)
                    } else {
                        cancelAutofillForNode(autofillNode)
                    }
                }
            }
        }


    if (borderShape == null) modifier = modifier.indicatorLine(
        enabled,
        state.isError.value,
        interactionSource,
        colors,
        focusedIndicatorLineThickness = focusedBorderThickness,
        unfocusedIndicatorLineThickness = unfocusedBorderThickness
    )

    CompositionLocalProvider(LocalTextSelectionColors provides textSelectionColors) {
        BasicTextField(
            value = state.text.value,
            modifier = modifier,
            onValueChange = onValueChange.withMaxLength(state.maxLength.value),
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
    val textColor = colors?.textColor ?: Color.Black
    val placeholderColor = colors?.placeholderColor ?: Color.Gray
    val focusedIndicatorColor = colors?.focusedIndicatorColor ?: Color(BorderConstants.focusedBorderColor)
    val unfocusedIndicatorColor = colors?.unfocusedIndicatorColor ?: Color(BorderConstants.unfocusedBorderColor)
    val disabledIndicatorColor = colors?.disabledIndicatorColor ?: Color(BorderConstants.disabledBorderColor)
    val errorIndicatorColor = colors?.errorIndicatorColor ?: Color(BorderConstants.errorBorderColor)
    val containerColor = colors?.containerColor ?: Color.Transparent
    val cursorColor = colors?.cursorColor ?: colors?.focusedIndicatorColor ?: Color.Black
    val errorCursorColor =
        colors?.errorCursorColor ?: colors?.errorIndicatorColor ?: Color(BorderConstants.errorBorderColor)

    return if (withBorder) outlinedTextFieldColors(
        textColor = textColor,
        placeholderColor = placeholderColor,
        focusedBorderColor = focusedIndicatorColor,
        unfocusedBorderColor = unfocusedIndicatorColor,
        disabledBorderColor = disabledIndicatorColor,
        errorBorderColor = errorIndicatorColor,
        containerColor = containerColor,
        cursorColor = cursorColor,
        errorCursorColor = errorCursorColor
    ) else textFieldColors(
        textColor = textColor,
        placeholderColor = placeholderColor,
        focusedIndicatorColor = focusedIndicatorColor,
        unfocusedIndicatorColor = unfocusedIndicatorColor,
        disabledIndicatorColor = disabledIndicatorColor,
        errorIndicatorColor = errorIndicatorColor,
        containerColor = containerColor,
        cursorColor = cursorColor,
        errorCursorColor = errorCursorColor
    )
}

@Composable
private fun provideTextSelectionColors(inputColors: InputFieldColors?, cursorColor: Color): TextSelectionColors {
    return TextSelectionColors(
        handleColor = inputColors?.cursorHandleColor ?: cursorColor,
        backgroundColor = inputColors?.cursorHighlightColor ?: cursorColor.copy(alpha = 0.4f)
    )
}

@Composable
private fun provideTextStyle(style: InputFieldViewStyle): TextStyle {
    var textStyle = style.textStyle ?: LocalTextStyle.current

    if (style.forceLTR && LocalLayoutDirection.current == LayoutDirection.Rtl)
        textStyle = textStyle.copy(textDirection = TextDirection.Ltr, textAlign = TextAlign.End)

    return textStyle
}

private fun ((String) -> Unit).withMaxLength(maxLength: Int?): (String) -> Unit = {
    maxLength?.let { limit ->
        if (it.length <= limit) this(it)
    } ?: run { this(it) }
}

/* ------------------------------ Preview ------------------------------ */

@OptIn(ExperimentalComposeUiApi::class)
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
                placeholder = { Text(text = "Test placeholder") }
            ),
            InputFieldState(text)
        ) { text.value = it }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
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
                containerShape = RoundedCornerShape(12.dp)
            ),
            InputFieldState(text)
        ) { text.value = it }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
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
                borderShape = RectangleShape
            ),
            InputFieldState(text)
        ) { text.value = it }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
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
                colors = InputFieldColors(unfocusedIndicatorColor = Color.Blue),
                borderShape = CutCornerShape(16.dp),
                unfocusedBorderThickness = 4.dp
            ),
            InputFieldState(text)
        ) { text.value = it }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
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
                colors = InputFieldColors(unfocusedIndicatorColor = Color.Magenta),
                borderShape = CircleShape,
                unfocusedBorderThickness = 2.dp
            ),
            InputFieldState(text)
        ) { text.value = it }
    }
}
