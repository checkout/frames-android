package com.checkout.frames.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

/**
 * @param text input text value for the field
 * @param leadingIcon the optional leading icon to be displayed at the beginning of the text field
 * container
 * @param trailingIcon the optional trailing icon to be displayed at the end of the text field
 * container
 * @param isError control error state of the field, enable or disable visual error elements of the field
 */
internal data class InputFieldState(
    val text: MutableState<String> = mutableStateOf(""),
    val leadingIcon: MutableState<@Composable (() -> Unit)?> = mutableStateOf(null),
    val trailingIcon: MutableState<@Composable (() -> Unit)?> = mutableStateOf(null),
    val isError: MutableState<Boolean> = mutableStateOf(false)
)
