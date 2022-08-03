package com.checkout.frames.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

/**
 * @param text text value for the label
 * @param leadingIcon the optional leading icon to be displayed at the beginning of the text field container
 * @param trailingIcon the optional trailing icon to be displayed at the end of the text field container
 * @param isVisible control visibility of the label
 */
internal data class TextLabelState(
    val text: MutableState<String> = mutableStateOf(""),
    val leadingIcon: MutableState<@Composable (() -> Unit)?> = mutableStateOf(null),
    val trailingIcon: MutableState<@Composable (() -> Unit)?> = mutableStateOf(null),
    val isVisible: MutableState<Boolean> = mutableStateOf(false)
)
