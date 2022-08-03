package com.checkout.frames.style.view

import androidx.compose.ui.Modifier

internal data class InputComponentViewStyle(
    val titleStyle: TextLabelViewStyle,
    val subtitleStyle: TextLabelViewStyle,
    val infoStyle: TextLabelViewStyle,
    val inputFieldStyle: InputFieldViewStyle = InputFieldViewStyle(),
    val errorMessageStyle: TextLabelViewStyle,
    val containerModifier: Modifier = Modifier
)
