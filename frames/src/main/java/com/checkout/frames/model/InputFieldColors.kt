package com.checkout.frames.model

import androidx.compose.ui.graphics.Color

internal data class InputFieldColors(
    val textColor: Color? = null,
    val placeholderColor: Color? = null,
    val focusedIndicatorColor: Color? = null,
    val unfocusedIndicatorColor: Color? = null,
    val disabledIndicatorColor: Color? = null,
    val errorIndicatorColor: Color? = null,
    val containerColor: Color = Color.Transparent,
    val cursorColor: Color? = null,
    val errorCursorColor: Color? = null,
    val cursorHandleColor: Color? = null,
    val cursorHighlightColor: Color? = null
)
