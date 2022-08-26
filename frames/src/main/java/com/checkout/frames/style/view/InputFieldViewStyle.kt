package com.checkout.frames.style.view

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import com.checkout.frames.model.InputFieldColors

/**
 * Style used to setup input field.
 *
 * @param value the input text to be shown in the text field
 * @param onValueChange the callback that is triggered when the input service updates the text. An
 * updated text comes as a parameter of the callback
 * @param modifier the [Modifier] to be applied to this text field
 * @param enabled controls the enabled state of this text field. When `false`, this component will
 * not respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param readOnly controls the editable state of the text field. When `true`, the text field cannot
 * be modified. However, a user can focus it and copy text from it. Read-only text fields are
 * usually used to display pre-filled forms that a user cannot edit.
 * @param textStyle the inputStyle to be applied to the input text. Defaults to [LocalTextStyle].
 * @param placeholder the optional placeholder to be displayed when the text field is in focus and
 * the input text is empty. The default text inputStyle for internal [Text] is [Typography.bodyLarge]
 * @param isError indicates if the text field's current value is in error. If set to true, the
 * label, bottom indicator and trailing icon by default will be displayed in error color
 * @param visualTransformation transforms the visual representation of the input [value]
 * For example, you can use
 * [PasswordVisualTransformation][androidx.compose.ui.text.input.PasswordVisualTransformation] to
 * create a password text field. By default, no visual transformation is applied.
 * @param keyboardOptions software keyboard options that contains configuration such as
 * [KeyboardType] and [ImeAction]
 * @param keyboardActions when the input service emits an IME action, the corresponding callback
 * is called. Note that this IME action may be different from what you specified in
 * [KeyboardOptions.imeAction]
 * @param singleLine when `true`, this text field becomes a single horizontally scrolling text field
 * instead of wrapping onto multiple lines. The keyboard will be informed to not show the return key
 * as the [ImeAction]. Note that [maxLines] parameter will be ignored as the maxLines attribute will
 * be automatically set to 1.
 * @param maxLines the maximum height in terms of maximum number of visible lines. Should be
 * equal or greater than 1. Note that this parameter will be ignored and instead maxLines will be
 * set to 1 if [singleLine] is set to true.
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this text field. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this text field in different states.
 * @param containerShape defines the shape of this text field's content/background.
 * @param borderShape defines the shape of this text field's border.
 * @param colors [TextFieldColors] that will be used to resolve the colors used for this text field
 * in different states. See [TextFieldDefaults.outlinedTextFieldColors].
 * @param focusedBorderThickness thickness of the text field's border when it is in
 * focused state.
 * @param unfocusedBorderThickness thickness of the text field's border when it is not
 * in focused state.
 * @param forceLTR force LTR usage even for RTL layout direction.
 */
internal data class InputFieldViewStyle(
    val modifier: Modifier = Modifier.fillMaxWidth(),
    val enabled: Boolean = true,
    val readOnly: Boolean = false,
    val textStyle: TextStyle? = null,
    val placeholder: @Composable (() -> Unit)? = null,
    val visualTransformation: VisualTransformation = VisualTransformation.None,
    val keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    val keyboardActions: KeyboardActions = KeyboardActions.Default,
    val singleLine: Boolean = true,
    val maxLines: Int = Int.MAX_VALUE,
    val interactionSource: MutableInteractionSource? = null,
    val containerShape: Shape = RectangleShape,
    val borderShape: Shape? = null,
    val colors: InputFieldColors? = null,
    val focusedBorderThickness: Dp = TextFieldDefaults.FocusedBorderThickness,
    val unfocusedBorderThickness: Dp = TextFieldDefaults.UnfocusedBorderThickness,
    var forceLTR: Boolean = false
)
