package com.checkout.frames.model

import androidx.compose.ui.graphics.Color

internal data class InputFieldColors(
    val textColor: Color? = null,
    val placeholderColor: Color? = null,
    val focusedIndicatorColor: Color? = null,
    val unfocusedIndicatorColor: Color? = null,
    val errorIndicatorColor: Color? = null,
    val containerColor: Color = Color.Transparent
)
